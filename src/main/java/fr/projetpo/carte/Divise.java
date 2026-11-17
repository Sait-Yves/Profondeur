package fr.projetpo.carte;

import fr.projetpo.*;

public class Divise extends CarteM{
    @Override
    public void effet(Entite me, Entite h1) {
        int H = 1 + (h1.getPvnow() / 12);
        System.out.println("Hexaghost effectue Divise, infligeant " + H + " dégâts 6 fois.");
        for (int i = 0; i < 6; i++) {
            h1.faireDegat(me, h1, H); // Inflige H dégâts au Hero
        }
    }    
}
