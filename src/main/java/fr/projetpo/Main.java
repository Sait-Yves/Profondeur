package fr.projetpo;

import fr.projetpo.carte.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
            WebServer server = new WebServer(port);
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
            server.start();
            System.out.println("Interface disponible sur http://127.0.0.1:" + port);
        } catch (IOException | IllegalArgumentException exception) {
            System.err.println("Impossible de démarrer le serveur : " + exception.getMessage());
            System.exit(1);
        }
    }
}
