package fr.projetpo.carte;

import fr.projetpo.*;

// Carte Tenacité
public class Tenacite extends CarteH {
    { cout = 1; }
    private String name = "Tenacité (Ajoute 2 cartes Plaie à sa main, gagne 15 points de blocage)";

    @Override
    public void effet(Entite a, Entite b) {
        if(a instanceof Hero){
            Hero c= (Hero)a;
            c.getMain().add(new Plaie());
            c.getMain().add(new Plaie());
            a.setEnergie(a.getEnergie()-cout);
            a.getEtat().setBlock(a.getEtat().getBlock() + 15);
        }
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }

}