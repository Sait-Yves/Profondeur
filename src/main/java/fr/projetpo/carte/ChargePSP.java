package fr.projetpo.carte;

import fr.projetpo.*;


public class ChargePSP extends CarteM {

    @Override
    public void effet(Entite a, Entite b){
        a.faireDegat(a,b, 5);
    }
}
