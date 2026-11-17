package fr.projetpo.carte;

import fr.projetpo.*;

public class Manchette extends CarteH {
    { cout = 2; }
    private String name=" manchette ( 12 degât à la cible et lui inflige 2 de faiblesse)";
    @Override
    public void effet(Entite a, Entite b){
        a.faireDegat(a,b, 12);
        b.getEtat().setFaible(b.getEtat().getFaible()+2);
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
