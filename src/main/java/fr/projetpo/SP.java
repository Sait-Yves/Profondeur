package fr.projetpo;

import fr.projetpo.carte.*;


import java.util.*;

public class SP extends Monstre {
    // slime piquant
    //"SLIME PIQUANT"; 

    public SP() {
        super();
        this.pvmax = 30;
        deck.add(new CrachatSP());
        deck.add(new Lecher());
        this.pvnow = this.pvmax;
        this.name="SLIME PIQUANT";
    }

    public  void action(Monstre a, Hero b){
       
        int chance= new Random().nextInt(100);
        if(chance<70){//Lecher
            deck.get(1).effet(a,b);
            cicle= cicle-1;
        }
        else{//Crachat
            deck.get(0).effet(a,b);
            cicle= cicle-1;
        }
        a.etat.statutBaisse(a);
        
    }
}
