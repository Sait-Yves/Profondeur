package fr.projetpo.carte;

import fr.projetpo.*;

public class Brule  extends CarteM{
    @Override
    public void effet(Entite me, Entite h1) {
        System.out.println("Hexaghost effectue Brûle, infligeant 6 dégâts et ajoutant 1 Brûlure dans la défausse.");
        me.faireDegat(me, h1, 6); // Inflige 6 dégâts au Hero
        if(h1 instanceof Hero){
            Hero a= (Hero)h1;
            a.getDefausse().add(new Brulure());// Ajoute 1 Brûlure dans la défausse
        }
        
    }    
}
