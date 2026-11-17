package fr.projetpo;

import fr.projetpo.carte.*;


import java.util.*;
public class Machouilleur extends Monstre {
    

    public Machouilleur() {
        super();
        this.pvmax = 40;
        deck.add(new ChargeM());
        deck.add( new Morsure());
        deck.add(new Gronder());
        this.pvnow = this.pvmax;
        this.name="MACHOUILLEUR";
    }

    public  void action( Monstre a, Hero b){
        if(cicle==1){
            deck.get(0).effet(a,b);
            cicle= cicle+1;
        } else if(cicle==2){
            int chance= new Random().nextInt(100);
            if(chance<45){//gronder
                deck.get(2).effet(a,b);
                cicle= cicle-1;
            }
            else if(chance<75){//charge
                deck.get(0).effet(a,b);
                cicle= cicle-1;
            }
            else{//morsure
                deck.get(1).effet(a,b);
                cicle= cicle-1;
            }
        }
        a.etat.statutBaisse(a);
    }
}
