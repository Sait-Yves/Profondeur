package fr.projetpo.carte;

import fr.projetpo.*;

// Carte Saignée
public class Saignee extends CarteH {
    { cout = 0; }
    private String name = "Saignée (Perdez 3 points de vie puis gagnez 2 points d’énergie)";

    @Override
    public void effet(Entite a, Entite b) {
        a.setPvnow(a.getPvnow() - 3);
        a.setEnergie(a.getEnergie()-cout);
        a.setEnergie(a.getEnergie() + 2);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
