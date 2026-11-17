package fr.projetpo.carte;

import fr.projetpo.*;

public class Lecher extends CarteM {

    @Override
    public void effet(Entite a, Entite b){
        b.getEtat().setFaible(b.getEtat().getFaible()+1);
    }
    
}
