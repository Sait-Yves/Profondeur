package fr.projetpo.carte;

import fr.projetpo.*;

public class VagueFer extends CarteH {
    //vague de fer
    { cout = 1; }
    private String name=" vague de fer ( 5 degât à la cible et  puis gagne 5 de blockage)";
    @Override
    public void effet(Entite a, Entite b){
        a.faireDegat(a,b, 5);
        a.getEtat().setBlock(a.getEtat().getBlock()+5);
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
