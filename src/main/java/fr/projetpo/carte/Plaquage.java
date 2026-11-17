package fr.projetpo.carte;

import fr.projetpo.*;

public class Plaquage extends CarteH {
    { cout = 1; }
    private String name=" plaquage ( la cible reçoit un nombre de degât egale à votre blockage)";
    @Override
    public void effet(Entite a, Entite b){
        a.faireDegat(a,b,a.getEtat().getBlock());
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
