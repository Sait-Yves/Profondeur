package fr.projetpo;

import fr.projetpo.carte.Defense;
import fr.projetpo.carte.Frappe;
import fr.projetpo.carte.Gourdin;
import fr.projetpo.carte.Heurt;
import fr.projetpo.carte.Offrande;
import fr.projetpo.carte.VoleeCoups;
import java.util.ArrayList;

public final class EngineSmokeChecks {
    private EngineSmokeChecks() {
    }

    public static void main(String[] args) {
        verifyHeroAndCardProperties();
        verifyDamageAndHealing();
        verifyDiscardReshuffle();
        verifyCombatTurn();
        System.out.println("Vérifications du moteur réussies.");
    }

    private static void verifyHeroAndCardProperties() {
        Hero hero = new Hero("Test");
        check(hero.getPvmax() == 70 && hero.getPvnow() == 70, "Les PV initiaux du héros doivent être cohérents.");
        check(hero.getEnergie() == 3, "Le héros doit commencer avec 3 points d'énergie.");
        check(new Frappe().getCout() == 1, "Le coût de Frappe doit être initialisé.");
        check(new Defense().getCout() == 1, "Le coût de Défense doit être initialisé.");
        check(new Heurt().getCout() == 2, "Le coût de Heurt doit être initialisé.");
        check(new Gourdin().getCout() == 3, "Le coût de Gourdin doit être initialisé.");
        check(new Offrande().getCout() == 0, "Offrande doit être gratuite.");
        check(new VoleeCoups().getCout() == 1 && new VoleeCoups().getExilable() == 1,
                "Volée de coups doit coûter 1 énergie et être exilée après usage.");
    }

    private static void verifyDamageAndHealing() {
        Hero target = new Hero("Cible");
        PSP attacker = new PSP();
        target.getEtat().setBlock(5);
        attacker.faireDegat(attacker, target, 10);
        check(target.getPvnow() == 65 && target.getEtat().getBlock() == 0,
                "Le blocage doit absorber les dégâts avant les PV.");

        target.setPvnow(70);
        attacker.getEtat().setFaible(1);
        attacker.faireDegat(attacker, target, 10);
        check(target.getPvnow() == 63, "La faiblesse doit réduire les dégâts de 25 %.");

        target.setPvnow(70);
        attacker.getEtat().setFaible(0);
        target.getEtat().setVuln(1);
        attacker.faireDegat(attacker, target, 10);
        check(target.getPvnow() == 55, "La vulnérabilité doit augmenter les dégâts de 50 %.");

        target.setPvnow(20);
        target.soigner(10);
        check(target.getPvnow() == 30, "Les soins doivent s'ajouter aux PV actuels.");
        target.soigner(100);
        check(target.getPvnow() == 70, "Les soins ne doivent pas dépasser les PV maximum.");
    }

    private static void verifyDiscardReshuffle() {
        Hero hero = new Hero("Pioche");
        for (int i = 0; i < 10; i++) hero.piocheHero();
        check(hero.getDeck().empty(), "Le deck de départ doit avoir été entièrement pioché.");
        for (fr.projetpo.carte.Carte card : new ArrayList<>(hero.getMain().getCartes())) {
            hero.getMain().remove(card);
            hero.getDefausse().add(card);
        }
        hero.piocheHero();
        check(hero.getMain().size() == 1 && hero.getDeck().size() == 9 && hero.getDefausse().empty(),
                "La défausse doit être remélangée avant de piocher.");
    }

    private static void verifyCombatTurn() {
        WebGameSession session = new WebGameSession();
        session.start("Test", 2);
        check(session.stateJson().contains("\"phase\":\"combat\""), "Une partie doit commencer en combat.");
        session.playCard(0, 0);
        check(!session.stateJson().contains("\"energy\":3"), "Une carte doit consommer son coût en énergie.");
        session.endTurn();
        check(session.stateJson().contains("\"turn\":2"), "Le tour suivant doit être correctement initialisé.");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
