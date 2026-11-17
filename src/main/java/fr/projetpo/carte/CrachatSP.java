package fr.projetpo.carte;

import fr.projetpo.*;

public class CrachatSP extends CarteM{
    @Override
    public void effet(Entite a, Entite b){
        a.faireDegat(a,b, 8);
        Slime c= new Slime();
        c.effet(a,b);

    }
}
