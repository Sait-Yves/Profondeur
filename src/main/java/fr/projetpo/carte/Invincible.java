package fr.projetpo.carte;

import fr.projetpo.*;

public class Invincible extends CarteH {
    { cout = 2; }
    private String name = "Invincible (Gagne 30 points de blocage)";
    { exilable = 1; }
    @Override
    public void effet(Entite a, Entite b) {
        a.getEtat().setBlock(a.getEtat().getBlock() + 30);
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
