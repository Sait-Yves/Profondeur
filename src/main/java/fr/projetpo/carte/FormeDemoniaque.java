package fr.projetpo.carte;

import fr.projetpo.*;

public class FormeDemoniaque extends CarteH{
    { cout = 3; }
    private String name = "Forme démoniaque (Donne au joueur 2 points d’un statut spécial Forme démoniaque)";
    { exilable = 1; }
    @Override
    public void effet(Entite a, Entite b) {
        if(a instanceof Hero){
            Hero c= (Hero)a;
            c.setDemoniaque(c.getDemoniaque() + 2);
            a.setEnergie(a.getEnergie()-cout);
        }
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
