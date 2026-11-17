package fr.projetpo.carte;

import fr.projetpo.*;

public class Gourdin extends CarteH {
    { cout = 3; }
    private String name = "Gourdin (Inflige 32 points de dégâts à un ennemi)";

    @Override
    public void effet(Entite a, Entite b) {
        b.faireDegat(a, b, 32);
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}

