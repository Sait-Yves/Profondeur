package fr.projetpo.carte;

import fr.projetpo.*;

public class CrachatSA extends CarteM{
    @Override
    public void effet(Entite a, Entite b){
        a.faireDegat(a,b, 7);
        Slime c= new Slime();
        c.effet(a,b);

    }
}