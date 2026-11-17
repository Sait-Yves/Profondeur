package fr.projetpo;

import fr.projetpo.carte.*;

public class Etat implements Cloneable {
    
    public int getBlock() {
        return block;
    }
    public void setBlock(int block) {
        this.block = block;
    }
    private int block;
    
    
    public int getForce() {
        return force;
    }
    public void setForce(int force) {
        this.force = force;
    }
    private int force;
   
    
    public int getFaible() {
        return faible;
    }
    public void setFaible(int faible) {
        this.faible = faible;
    }
    private int faible;
    
    
    private int vuln;
    public int getVuln() {
        return vuln;
    }
    public void setVuln(int vuln) {
        this.vuln = vuln;
    }
    
    
    public int getFragile() {
        return fragile;
    }
    public void setFragile(int fragile) {
        this.fragile = fragile;
    }
    private int fragile;
    
    
    
    public Etat(int block, int force, int faible, int vuln, int fragile) {
        this.block = block;
        this.force = force;
        this.faible = faible;
        this.vuln = vuln;
        this.fragile = fragile;
    }

    public void statutBaisse(Entite entite) {
        // Baisser la force
        if (entite.getEtat().getForce() >= 1) {
            entite.getEtat().setForce(entite.getEtat().getForce() - 1);
        }
    
        // Baisser la faiblesse
        if (entite.getEtat().getFaible() >= 1) {
            entite.getEtat().setFaible(entite.getEtat().getFaible() - 1);
        }
    
        // Baisser la vulnérabilité
        if (entite.getEtat().getVuln() >= 1) {
            entite.getEtat().setVuln(entite.getEtat().getVuln() - 1);
        }
    
        // Baisser la fragilité
        if (entite.getEtat().getFragile() >= 1) {
            entite.getEtat().setFragile(entite.getEtat().getFragile() - 1);
        }
    }
    
    @Override
    public Etat clone() {
        try {
            return (Etat) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Impossible de cloner l'état", e);
        }
    }

    public void statutReset() {
        setFaible(0);
        setVuln(0);
        setFragile(0);
    }
    
    
}
