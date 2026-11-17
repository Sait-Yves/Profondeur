package fr.projetpo;

import fr.projetpo.carte.*;



public abstract class Monstre extends Entite {
    protected int cicle=0;
    
    public Monstre() {
        super();
        this.cicle=1;
    }

    
    @Override
    public Monstre clone() {
        Monstre clone = (Monstre) super.clone();       
        clone.cicle = this.cicle;

        if (deck != null) {
            clone.deck = deck.clone();
        }
        if (etat != null) {
            clone.etat = etat.clone();
        }
        return clone;
    }
    
    public abstract  void action( Monstre a,Hero b);
}
