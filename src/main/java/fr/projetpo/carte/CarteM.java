package fr.projetpo.carte;

import fr.projetpo.*;

public abstract class CarteM extends Carte {
    
    public abstract void effet(Entite a, Entite b);
    public int getCout() {
        return cout;
    }
}
