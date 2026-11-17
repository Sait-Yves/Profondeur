package fr.projetpo.carte;

import fr.projetpo.*;

public class Enflammer extends CarteH {
    { cout = 1; }
    private String name = "Enflammer (Gagne 2 de Force. Cette carte est mise en exil après avoir été jouée)";
    { exilable = 1; }
    @Override
    public void effet(Entite a, Entite b) {
        a.getEtat().setForce(a.getEtat().getForce() + 2);
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}