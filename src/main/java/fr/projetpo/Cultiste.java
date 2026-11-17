package fr.projetpo;

import fr.projetpo.carte.*;


public class Cultiste extends Monstre{

    private int rituel;
     

    public int getRituel() {
        return rituel;
    }

    public void setRituel(int rituel) {
        this.rituel = rituel;
    }

    public Cultiste() {
        super();
        this.pvmax = 50;
        deck.add(new Rituel());
        deck.add(new Attaque());
        this.pvnow = this.pvmax;
        this.name="CULTISTE";
    }

    public  void action(Monstre a, Hero b){
        if(cicle==1){
            deck.get(0).effet(a,b);
            cicle= cicle+1;
        } else if(cicle==2){
            deck.get(1).effet(a,b);
            cicle= cicle-1;
        }
        etat.setForce(etat.getForce()+getRituel());
        a.etat.statutBaisse(a);
        if(rituel>0)rituel--;
    }

    @Override
public Cultiste clone() {
    Cultiste clone = (Cultiste) super.clone();
    
    clone.rituel = this.rituel;
    
    if (deck != null) {
        clone.deck = deck.clone();
    }
    if (etat != null) {
        clone.etat = etat.clone();
    }
    return clone;
}
    
}
