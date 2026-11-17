package fr.projetpo.carte;

import fr.projetpo.*;

public class Gronder extends CarteM{
    @Override
    public void effet(Entite a, Entite b){
        a.getEtat().setBlock(a.getEtat().getBlock()+6);
        a.getEtat().setForce(a.getEtat().getForce()+3);
    }
}
