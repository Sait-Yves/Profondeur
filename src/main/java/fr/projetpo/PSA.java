package fr.projetpo;

import fr.projetpo.carte.*;



public class PSA extends Monstre {
    //"PETIT SLIME ACIDE"; 

    public PSA() {
        super();
        this.pvmax = 8;
        deck.add(new ChargePSA());
        deck.add( new Lecher());
        this.pvnow = this.pvmax;
        this.name="PETIT SLIME ACIDE";
    }

    public  void action(Monstre a, Hero b){
        if(cicle==1){
            deck.get(1).effet(a,b);
            cicle= cicle+1;
        } else if(cicle==2){
            deck.get(0).effet(a,b);
            cicle= cicle-1;
        }
        a.etat.statutBaisse(a);
    }
}
