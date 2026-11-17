package fr.projetpo.carte;

import fr.projetpo.*;

public class Enflamme extends CarteM{

    public void effet(Entite me, Entite h1) {
        System.out.println("Hexaghost effectue Enflamme, gagnant 12 points de blocage et 2 Force.");
        me.getEtat().setBlock(me.getEtat().getBlock()+12);
        me.getEtat().setForce(me.getEtat().getForce()+2); 
    }
}
