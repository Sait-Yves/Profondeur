package fr.projetpo;

import fr.projetpo.carte.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebServer {
    private static final int MAX_REQUEST_BYTES = 16_384;
    private final WebGameSession session = new WebGameSession();
    private final HttpServer server;

    public WebServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
        server.createContext("/api/", this::handleApi);
        server.createContext("/", this::handleStatic);
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(1);
    }

    private void handleApi(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            if ("GET".equals(exchange.getRequestMethod()) && "/api/state".equals(path)) {
                send(exchange, 200, "application/json; charset=utf-8", session.stateJson());
                return;
            }
            if (!"POST".equals(exchange.getRequestMethod())) {
                send(exchange, 405, "application/json; charset=utf-8", "{\"error\":\"Méthode non autorisée.\"}");
                return;
            }

            String request = readBody(exchange.getRequestBody());
            if ("/api/start".equals(path)) {
                session.start(stringField(request, "name", "Aventurier"), integerField(request, "difficulty", 1));
            } else if ("/api/play".equals(path)) {
                session.playCard(integerField(request, "card", -1), integerField(request, "target", -1));
            } else if ("/api/end-turn".equals(path)) {
                session.endTurn();
            } else if ("/api/reward".equals(path)) {
                session.chooseReward(integerField(request, "index", -1));
            } else if ("/api/rest".equals(path)) {
                session.chooseRest(integerField(request, "choice", -1), integerField(request, "index", -1));
            } else if ("/api/continue".equals(path)) {
                session.continueEvent();
            } else {
                send(exchange, 404, "application/json; charset=utf-8", "{\"error\":\"Action inconnue.\"}");
                return;
            }
            send(exchange, 200, "application/json; charset=utf-8", session.stateJson());
        } catch (IllegalArgumentException exception) {
            send(exchange, 400, "application/json; charset=utf-8", "{\"error\":\"" + escapeJson(exception.getMessage()) + "\"}");
        } catch (RuntimeException exception) {
            System.err.println("Erreur API : " + exception.getMessage());
            exception.printStackTrace(System.err);
            send(exchange, 500, "application/json; charset=utf-8", "{\"error\":\"Une erreur interne est survenue.\"}");
        }
    }

    private void handleStatic(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            send(exchange, 405, "text/plain; charset=utf-8", "Méthode non autorisée.");
            return;
        }
        String path = exchange.getRequestURI().getPath();
        String fileName;
        String contentType;
        if ("/".equals(path) || "/index.html".equals(path)) {
            fileName = "index.html";
            contentType = "text/html; charset=utf-8";
        } else if ("/styles.css".equals(path)) {
            fileName = "styles.css";
            contentType = "text/css; charset=utf-8";
        } else if ("/app.js".equals(path)) {
            fileName = "app.js";
            contentType = "text/javascript; charset=utf-8";
        } else {
            send(exchange, 404, "text/plain; charset=utf-8", "Ressource introuvable.");
            return;
        }

        try (InputStream resource = WebServer.class.getResourceAsStream("/web/" + fileName)) {
            if (resource != null) {
                send(exchange, 200, contentType, new String(resource.readAllBytes(), StandardCharsets.UTF_8));
                return;
            }
        }
        Path file = Path.of("web", fileName);
        if (!Files.isRegularFile(file)) {
            send(exchange, 404, "text/plain; charset=utf-8", "Ressource introuvable.");
            return;
        }
        send(exchange, 200, contentType, Files.readString(file, StandardCharsets.UTF_8));
    }

    private static String readBody(InputStream input) throws IOException {
        byte[] bytes = input.readNBytes(MAX_REQUEST_BYTES + 1);
        if (bytes.length > MAX_REQUEST_BYTES) {
            throw new IllegalArgumentException("La requête est trop volumineuse.");
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static int integerField(String json, String key, int fallback) {
        Matcher matcher = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*(-?\\d+)").matcher(json);
        if (!matcher.find()) return fallback;
        try {
            return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Valeur numérique invalide pour " + key + ".");
        }
    }

    private static String stringField(String json, String key, String fallback) {
        Matcher matcher = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"").matcher(json);
        if (!matcher.find()) return fallback;
        String value = matcher.group(1);
        return value.replace("\\\"", "\"").replace("\\\\", "\\").trim();
    }

    private static String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }

    private static void send(HttpExchange exchange, int status, String contentType, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.getResponseHeaders().set("X-Content-Type-Options", "nosniff");
        exchange.getResponseHeaders().set("Cache-Control", "no-store");
        exchange.sendResponseHeaders(status, bytes.length);
        try {
            exchange.getResponseBody().write(bytes);
        } finally {
            exchange.close();
        }
    }
}
