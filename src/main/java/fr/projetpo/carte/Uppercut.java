package fr.projetpo.carte;

import fr.projetpo.*;

public class Uppercut extends CarteH {
    { cout = 2; }
    private String name = "Uppercut (Inflige 13 points de dégâts à un ennemi, puis inflige 1 point de Faiblesse et Vulnérable)";

    @Override
    public void effet(Entite a, Entite b) {
        a.faireDegat(a, b, 13);
        b.getEtat().setFaible(b.getEtat().getFaible() + 1);
        b.getEtat().setVuln(b.getEtat().getVuln() + 1);
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}

