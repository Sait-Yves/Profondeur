package fr.projetpo.carte;

import fr.projetpo.*;

// Carte Onde de choc
public class OndeChoc extends CarteH {
    { cout = 2; }
    private String name = "Onde de choc (Inflige 3 points de Faiblesse et de Vulnérables à tous les ennemis. Cette carte est mise en exil après avoir été jouée)";
    { exilable = 1; }
    @Override
    public void effet(Entite a, Entite b) {
        if(a instanceof Hero){
            Hero c= (Hero)a;
            for (Entite ennemi : c.getSalle().getGang()) {
                ennemi.getEtat().setFaible(ennemi.getEtat().getFaible() + 3);
                ennemi.getEtat().setVuln(ennemi.getEtat().getVuln() + 3);
            }
            a.setEnergie(a.getEnergie()-cout);
        }
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}