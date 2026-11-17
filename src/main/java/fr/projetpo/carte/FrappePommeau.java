package fr.projetpo.carte;

import fr.projetpo.*;

public class FrappePommeau extends CarteH {
    { cout = 1; }
    private String name=" frappe du pommeau ( 9 degât à la cible et  pioche une carte)";
    @Override
    public void effet(Entite a, Entite b){
        a.faireDegat(a,b, 9);
        if (a instanceof Hero) {
            Hero h = (Hero) a;
            h.piocheHero();
        }
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
