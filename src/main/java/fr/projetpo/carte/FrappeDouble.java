package fr.projetpo.carte;

import fr.projetpo.*;

public class FrappeDouble extends CarteH {
    { cout = 1; }
    private String name="frappe double (5 de degât 2 fois à la même cible)";
    @Override
    public void effet(Entite a, Entite b){
        a.faireDegat(a,b, 5);
        a.faireDegat(a,b,5);
        a.setEnergie(a.getEnergie()-cout);
       
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
