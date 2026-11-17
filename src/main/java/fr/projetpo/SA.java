package fr.projetpo;

import fr.projetpo.carte.*;


import java.util.*;

public class SA extends Monstre {
    //"SLIME ACIDE"; 
    public SA() {
        super();
        this.pvmax = 30;
        deck.add(new ChargeSA());
        deck.add(new CrachatSA());
        deck.add(new Lecher());
        this.pvnow = this.pvmax;
        this.name="SLIME ACIDE";
    }

    public  void action(Monstre a, Hero b){
       
        int chance= new Random().nextInt(100);
        if(chance<40){//Crachat
            deck.get(1).effet(a,b);
            cicle= cicle-1;
        } else if(chance<70){//Lecher
            deck.get(2).effet(a,b);
            cicle= cicle-1;
        }
        else{
            deck.get(0).effet(a,b);
            cicle= cicle-1;
        }

        a.etat.statutBaisse(a);
        
    }
}
