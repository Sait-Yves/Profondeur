package fr.projetpo.carte;

import fr.projetpo.*;

public class Desarmement extends CarteH {
    { cout = 1; }
    private String name = "Désarmement (Fait perdre 2 de Force à un ennemi. Cette carte est mise en exil après avoir été jouée)";
    { exilable = 1; }
    @Override
    public void effet(Entite a, Entite b) {
        b.getEtat().setForce(b.getEtat().getForce() - 2);
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
