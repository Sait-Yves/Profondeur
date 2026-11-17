package fr.projetpo.carte;

import fr.projetpo.*;

public class Enfer extends CarteM{
    @Override
    public void effet(Entite me, Entite h1) {
        System.out.println("Hexaghost effectue Enfer, infligeant 6 dégâts 2 fois et ajoutant 3 Brûlure dans la défausse.");
        for (int i = 0; i < 2; i++) {
            me.faireDegat(me, h1, 6); // Inflige 6 dégâts au Hero
        }
        if(h1 instanceof Hero){
            Hero a= (Hero)h1;
            a.getDefausse().add(new Brulure());// Ajoute 3 Brûlure dans la défausse
            a.getDefausse().add(new Brulure());
            a.getDefausse().add(new Brulure());
        }
    }    
}
