package fr.projetpo.carte;

import fr.projetpo.*;

public class Charge extends CarteM{
    @Override
    public void effet(Entite me, Entite h1) {
        System.out.println("Hexaghost effectue Charge, infligeant 5 dégâts 2 fois.");
        for (int i = 0; i < 2; i++) {
           me.faireDegat(me, h1, 5); // Inflige 5 dégâts au Hero
        }
    }
}
