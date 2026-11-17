package fr.projetpo.carte;

import fr.projetpo.*;


public abstract class Carte implements Cloneable {
    protected int exilable;
    protected int cout;
    protected String name;
    
    public int getCout(){
        return this.cout;
    }
    public int getExilable() {
        return exilable;
    }

    @Override
    public Carte clone() {
        try {
            return (Carte) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Impossible de cloner la carte", e);
        }
    }

    public abstract void effet(Entite a, Entite b);
    
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
