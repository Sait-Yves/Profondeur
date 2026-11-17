package fr.projetpo.carte;

import fr.projetpo.*;

// Carte Volée de coups
public class VoleeCoups extends CarteH {
    { cout = 1; }
    private String name = "Volée de coups (Inflige 2 dégâts 4 fois à un ennemi. Cette carte est mise en exil après avoir été jouée)";
    { exilable = 1; }
    @Override
    public void effet(Entite a, Entite b) {
        for (int i = 0; i < 4; i++) {
            a.faireDegat(a,b,2);
        }
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
