package fr.projetpo.carte;

import fr.projetpo.*;

public class Frappe extends CarteH {
    { cout = 1; }
    private String name="frappe (6 de degât à la cible)";
    
    @Override
    public int getCout(){
        return this.cout;
    }

    @Override
    public void effet(Entite a, Entite b){
        a.faireDegat(a,b, 6);
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
    
}