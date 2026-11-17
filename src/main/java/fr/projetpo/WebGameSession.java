package fr.projetpo;

import fr.projetpo.carte.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WebGameSession {
    private Hero hero;
    private ArrayList<Salle> route;
    private Salle salle;
    private int position;
    private int restChoices;
    private int turn;
    private String phase = "title";
    private String message = "Bienvenue dans les profondeurs.";
    private final List<String> journal = new ArrayList<>();

    public synchronized void start(String name, int difficulty) {
        if (difficulty < 1 || difficulty > 3) {
            throw new IllegalArgumentException("La difficulté doit être comprise entre 1 et 3.");
        }

        hero = new Hero(name);
        Chemin chemin = new Chemin();
        if (difficulty == 1) {
            chemin.easy();
        } else if (difficulty == 2) {
            chemin.mid();
        } else {
            chemin.hard();
        }
        route = chemin.getRoute();
        position = 0;
        turn = 1;
        journal.clear();
        log("L'aventure de " + hero.getName() + " commence.");
        openNextRoom();
    }

    public synchronized void playCard(int cardIndex, int targetIndex) {
        requirePhase("combat");
        if (cardIndex < 0 || cardIndex >= hero.getMain().size()) {
            throw new IllegalArgumentException("Cette carte n'est plus dans votre main.");
        }
        List<Entite> enemies = livingEnemies();
        if (targetIndex < 0 || targetIndex >= enemies.size()) {
            throw new IllegalArgumentException("Choisissez une cible encore en vie.");
        }

        Carte card = hero.getMain().get(cardIndex);
        if (!(card instanceof CarteH)) {
            throw new IllegalArgumentException("Cette carte ne peut pas être jouée par le héros.");
        }
        if (card.getCout() > hero.getEnergie()) {
            throw new IllegalArgumentException("Vous n'avez pas assez d'énergie.");
        }

        hero.action(card, hero, enemies.get(targetIndex));
        message = "Vous jouez " + card.presentation() + ".";
        log("Carte jouée : " + card.presentation() + ".");
        if (!hero.alive()) {
            phase = "gameOver";
            message = "Cette carte vous a coûté vos derniers points de vie.";
            log(message);
            return;
        }
        finishCombatIfNeeded();
    }

    public synchronized void endTurn() {
        requirePhase("combat");
        for (Carte card : new ArrayList<>(hero.getMain().getCartes())) {
            hero.getMain().remove(card);
            hero.getDefausse().add(card);
            if (card instanceof Brulure) {
                hero.setPvnow(hero.getPvnow() - 2);
                log("Une brûlure inflige 2 dégâts.");
            }
        }

        if (!hero.alive()) {
            phase = "gameOver";
            message = "Votre aventure s'arrête ici.";
            return;
        }

        hero.getEtat().setBlock(0);
        for (Entite enemy : livingEnemies()) {
            ((Monstre) enemy).action((Monstre) enemy, hero);
            if (!hero.alive()) {
                break;
            }
        }
        if (!hero.alive()) {
            phase = "gameOver";
            message = "Votre aventure s'arrête ici.";
            log("Vous avez été vaincu.");
            return;
        }

        if (livingEnemies().isEmpty()) {
            winCombat();
            return;
        }

        turn++;
        hero.setEnergie(3);
        for (int i = 0; i < 5; i++) {
            hero.piocheHero();
        }
        message = "Tour " + turn + " : votre énergie et votre main sont renouvelées.";
        log("Tour " + turn + " : 5 cartes piochées.");
    }

    public synchronized void chooseReward(int rewardIndex) {
        requirePhase("reward");
        List<Carte> rewards = salle.getRewardDeck().getCartes();
        if (rewardIndex < 0 || rewardIndex >= rewards.size()) {
            throw new IllegalArgumentException("Choisissez une récompense valide.");
        }
        Carte reward = rewards.get(rewardIndex);
        hero.getDeck().add(reward.clone());
        log("Carte ajoutée au deck : " + reward.presentation() + ".");
        position++;
        openNextRoom();
    }

    public synchronized void chooseRest(int choice) {
        chooseRest(choice, -1);
    }

    public synchronized void chooseRest(int choice, int rewardIndex) {
        requirePhase("rest");
        if (choice == 8) {
            log("Vous quittez le feu de camp.");
            position++;
            openNextRoom();
            return;
        }
        if (restChoices >= 2) {
            throw new IllegalArgumentException("Vous avez déjà effectué deux actions à ce feu de camp.");
        }

        if (choice == 1) {
            if (hero.getCoin() < 30) {
                throw new IllegalArgumentException("Il vous faut 30 pièces pour ajouter une carte.");
            }
            List<Carte> restRewards = salle.getRewardDeck().getCartes();
            if (rewardIndex < 0 || rewardIndex >= restRewards.size()) {
                throw new IllegalArgumentException("Choisissez une carte valide.");
            }
            Carte card = restRewards.get(rewardIndex);
            hero.getDeck().add(card.clone());
            hero.setCoin(hero.getCoin() - 30);
            restChoices++;
            log("Carte achetée au feu de camp : " + card.presentation() + ".");
            if (restChoices == 2) {
                position++;
                openNextRoom();
            }
            return;
        }

        int cost;
        switch (choice) {
            case 2: cost = 10; break;
            case 3: cost = 5; break;
            case 4: cost = 7; break;
            case 5: cost = 10; break;
            case 6: cost = 10; break;
            case 7: cost = 10; break;
            default: throw new IllegalArgumentException("Choisissez une option de repos valide.");
        }
        if (hero.getCoin() < cost) {
            throw new IllegalArgumentException("Vous n'avez pas assez de pièces pour cette action.");
        }

        switch (choice) {
            case 2:
                hero.getEtat().statutReset();
                log("Vos altérations négatives sont dissipées.");
                break;
            case 3:
                hero.soigner(15);
                log("Vous récupérez jusqu'à 15 PV.");
                break;
            case 4:
                hero.soigner(45);
                log("Vous récupérez jusqu'à 45 PV.");
                break;
            case 5:
                hero.soigner(70);
                log("Vous récupérez jusqu'à 70 PV.");
                break;
            case 6:
                hero.getEtat().setBlock(hero.getEtat().getBlock() + 10);
                log("Vous gagnez 10 points de blocage.");
                break;
            case 7:
                hero.getEtat().setForce(hero.getEtat().getForce() + 5);
                log("Vous gagnez 5 points de force.");
                break;
            default:
                throw new IllegalArgumentException("Option de repos inconnue.");
        }
        hero.setCoin(hero.getCoin() - cost);
        restChoices++;
        if (restChoices == 2) {
            position++;
            openNextRoom();
        }
    }

    public synchronized void continueEvent() {
        requirePhase("event");
        position++;
        openNextRoom();
    }

    public synchronized String stateJson() {
        StringBuilder json = new StringBuilder("{");
        field(json, "phase", phase).append(',');
        field(json, "message", message).append(',');
        json.append("\"room\":").append(hero == null ? 0 : Math.min(position + 1, route.size())).append(',');
        json.append("\"roomCount\":").append(route == null ? 0 : route.size()).append(',');
        json.append("\"turn\":").append(turn).append(',');
        json.append("\"restChoices\":").append(restChoices).append(',');
        json.append("\"hero\":");
        if (hero == null) {
            json.append("null");
        } else {
            appendEntity(json, hero);
        }
        json.append(",\"hand\":[");
        if (hero != null) {
            appendCards(json, hero.getMain().getCartes());
        }
        json.append("],\"enemies\":[");
        if (salle instanceof SalleCombat) {
            appendEntities(json, livingEnemies());
        }
        json.append("],\"rewards\":[");
        if ("reward".equals(phase) || ("rest".equals(phase) && hero.getCoin() >= 30 && restChoices < 2)) {
            appendCards(json, salle.getRewardDeck().getCartes());
        }
        json.append("],\"journal\":[");
        for (int i = 0; i < journal.size(); i++) {
            if (i > 0) json.append(',');
            quote(json, journal.get(i));
        }
        return json.append("]}").toString();
    }

    private void openNextRoom() {
        if (hero == null || !hero.alive()) {
            phase = "gameOver";
            return;
        }
        if (position >= route.size()) {
            phase = "victory";
            message = "Le donjon est vaincu. La victoire est à vous !";
            log(message);
            return;
        }

        salle = route.get(position);
        hero.setSalle(salle);
        restChoices = 0;
        if (salle instanceof SalleCombat) {
            phase = "combat";
            turn = 1;
            hero.getMain().vider();
            hero.getDeck().melange(hero.getDeck(), hero.getExile());
            hero.getDeck().melange(hero.getDeck(), hero.getDefausse());
            hero.setEnergie(3);
            hero.getEtat().setBlock(0);
            for (int i = 0; i < 5; i++) hero.piocheHero();
            message = "Un combat commence.";
            log(message);
        } else if (salle instanceof SalleRepos) {
            phase = "rest";
            message = "Le feu de camp vous offre un moment de répit.";
            log(message);
        } else {
            int event = new Random().nextInt(10);
            if (event < 5) {
                hero.getEtat().setFaible(hero.getEtat().getFaible() + 2);
                hero.getEtat().setForce(hero.getEtat().getForce() + 3);
                message = "Une rencontre étrange vous affaiblit, mais vous rend plus puissant.";
            } else {
                message = "Vous traversez la salle sans encombre.";
            }
            phase = "event";
            log(message);
        }
    }

    private void finishCombatIfNeeded() {
        if (livingEnemies().isEmpty()) {
            winCombat();
        }
    }

    private void winCombat() {
        int coins = salle.getCoin();
        hero.setCoin(hero.getCoin() + coins);
        phase = "reward";
        message = "Victoire ! Vous récupérez " + coins + " pièces. Choisissez une carte.";
        for (Carte card : new ArrayList<>(hero.getMain().getCartes())) {
            hero.getMain().remove(card);
            hero.getDefausse().add(card);
        }
        log(message);
    }

    private List<Entite> livingEnemies() {
        List<Entite> enemies = new ArrayList<>();
        if (salle != null) {
            for (Entite entity : salle.getGang()) {
                if (entity.alive()) enemies.add(entity);
            }
        }
        return enemies;
    }

    private void requirePhase(String expected) {
        if (hero == null) {
            throw new IllegalArgumentException("Commencez une aventure avant de jouer.");
        }
        if (!expected.equals(phase)) {
            throw new IllegalArgumentException("Cette action n'est pas disponible à cette étape.");
        }
    }

    private void log(String entry) {
        journal.add(entry);
        if (journal.size() > 8) journal.remove(0);
    }

    private static void appendEntity(StringBuilder json, Entite entity) {
        json.append('{');
        field(json, "name", entity.getName()).append(',');
        json.append("\"hp\":").append(entity.getPvnow()).append(',');
        json.append("\"maxHp\":").append(entity.getPvmax()).append(',');
        json.append("\"block\":").append(entity.getEtat().getBlock()).append(',');
        json.append("\"strength\":").append(entity.getEtat().getForce()).append(',');
        json.append("\"weak\":").append(entity.getEtat().getFaible()).append(',');
        json.append("\"vulnerable\":").append(entity.getEtat().getVuln()).append(',');
        json.append("\"energy\":").append(entity.getEnergie()).append(',');
        json.append("\"coins\":").append(entity instanceof Hero ? ((Hero) entity).getCoin() : 0).append(',');
        json.append("\"deckSize\":").append(entity.getDeck().size()).append(',');
        json.append("\"discardSize\":").append(entity instanceof Hero ? ((Hero) entity).getDefausse().size() : 0);
        json.append('}');
    }

    private static void appendCards(StringBuilder json, List<Carte> cards) {
        for (int i = 0; i < cards.size(); i++) {
            if (i > 0) json.append(',');
            Carte card = cards.get(i);
            json.append('{');
            json.append("\"index\":").append(i).append(',');
            json.append("\"cost\":").append(card.getCout()).append(',');
            field(json, "name", card.presentation()).append(',');
            json.append("\"playable\":").append(card instanceof CarteH);
            json.append('}');
        }
    }

    private static void appendEntities(StringBuilder json, List<Entite> entities) {
        for (int i = 0; i < entities.size(); i++) {
            if (i > 0) json.append(',');
            json.append('{');
            json.append("\"index\":").append(i).append(',');
            field(json, "name", entities.get(i).getName()).append(',');
            json.append("\"hp\":").append(entities.get(i).getPvnow()).append(',');
            json.append("\"maxHp\":").append(entities.get(i).getPvmax()).append(',');
            json.append("\"block\":").append(entities.get(i).getEtat().getBlock());
            json.append('}');
        }
    }

    private static StringBuilder field(StringBuilder json, String key, String value) {
        quote(json, key);
        json.append(':');
        quote(json, value);
        return json;
    }

    private static void quote(StringBuilder json, String value) {
        json.append('"');
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            if (character == '"' || character == '\\') {
                json.append('\\').append(character);
            } else if (character == '\n') {
                json.append("\\n");
            } else if (character == '\r') {
                json.append("\\r");
            } else if (character == '\t') {
                json.append("\\t");
            } else if (character < 0x20) {
                json.append(String.format("\\u%04x", (int) character));
            } else {
                json.append(character);
            }
        }
        json.append('"');
    }
}
