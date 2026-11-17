package fr.projetpo.carte;

import fr.projetpo.*;

public class ChargeM extends CarteM {
    @Override
    public void effet(Entite a, Entite b){
        a.faireDegat(a,b, 7);
        a.getEtat().setBlock(a.getEtat().getBlock()+5);
    }
}
