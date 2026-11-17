package fr.projetpo.carte;

import fr.projetpo.*;

// Carte Voir rouge
public class VoirRouge extends CarteH {
    { cout = 1; }
    private String name = "Voir rouge (Gagne 2 points d’énergies. Cette carte est mise en exil après avoir été jouée)";
    { exilable = 1; }
    @Override
    public void effet(Entite a, Entite b) {
        a.setEnergie(a.getEnergie() + 2);
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
