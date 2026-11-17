package fr.projetpo;

import fr.projetpo.carte.*;

public  abstract class Entite implements Cloneable{
    public String getName() {
        return name;
    }

    protected String name;
    protected int energie;
    public int getEnergie() {
        return energie;
    }

    public void setEnergie(int energie) {
        this.energie = energie;
    }

    protected int pvmax;
    public int getPvmax() {
        return pvmax;
    }

    protected int pvnow;
    public int getPvnow() {
        return pvnow;
    }

    public void setPvnow(int pvnow) {
        this.pvnow = Math.max(0, pvmax > 0 ? Math.min(pvnow, pvmax) : pvnow);
    }

    protected Deck deck;
    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    protected Etat etat;
    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }
    
    
    public Entite() {
        
        this.deck= new Deck();
        this.deck.setName("deck");
        this.etat= new Etat(0,0,0,0,0);
    }
    public void faireDegat(Entite hit, Entite hitten, int degat){
        double res = Math.max(0, hit.getEtat().getForce() + degat);
        if(hit.getEtat().getFaible()>=1){
            res= Math.floor(res*0.75);
        }
        if(hitten.getEtat().getVuln()>=1){
            res=Math.floor(res*1.5);
        }
        int degats = (int) res;
        int absorbed = Math.min(hitten.etat.getBlock(), degats);
        hitten.etat.setBlock(hitten.etat.getBlock() - absorbed);
        int healthDamage = degats - absorbed;
        hitten.setPvnow(hitten.getPvnow() - healthDamage);
        System.out.println(hitten.getName()+" subit " + healthDamage + " dégâts (" + absorbed + " bloqués) et il lui reste " + hitten.getPvnow() + " PV");
        
    }


    @Override
    public Entite clone() {
        try {
            Entite clone = (Entite) super.clone();
            // Cloner les objets mutables s'ils existent
            if (deck != null) {
                clone.deck = deck.clone();
            }
            if (etat != null) {
                clone.etat = etat.clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Impossible de cloner l'entité", e);
        }
    }
    
    
    public boolean alive(){
        return (pvnow>0);
    }

    public String statut(){
        return "il possède actuellement "+etat.getForce()+" de force, "+etat.getBlock()+" de blockage, "+etat.getFaible()+" de faiblesse, "+etat.getVuln()+" de vulnérabilitée et "+etat.getFragile()+" de fragilitée.";
    }

    public String presentation(){
        return "Entité: "+name+"\n il lui reste "+pvnow+" de PV "+statut()+"\n";
    }
    
}