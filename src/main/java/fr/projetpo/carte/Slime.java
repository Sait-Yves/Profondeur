package fr.projetpo.carte;

import fr.projetpo.*;

public class Slime extends CarteM {
    @Override
    public void effet(Entite a, Entite b){
        b.getDeck().add(new Pollution());
    }
}
