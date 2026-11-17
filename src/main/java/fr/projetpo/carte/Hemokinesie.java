package fr.projetpo.carte;

import fr.projetpo.*;

// Carte Hémokinésie
public class Hemokinesie extends CarteH {
    { cout = 1; }
    private String name = "Hémokinésie (Perdez 2 points de vie, inflige 15 points de dégâts à un ennemi)";

    @Override
    public void effet(Entite a, Entite b) {
        a.setPvnow(a.getPvnow() - 2);
        a.faireDegat(a, b, 15);
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
