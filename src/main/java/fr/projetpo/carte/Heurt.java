package fr.projetpo.carte;

import fr.projetpo.*;

public class Heurt  extends CarteH {
    { cout = 2; }
    private String name=" heurt ( 8 degât à la cible et lui inflige 2 vulnérabilitée)";
    @Override
    public void effet(Entite a, Entite b){
        a.faireDegat(a,b, 8);
        b.getEtat().setVuln(b.getEtat().getVuln()+2);
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
