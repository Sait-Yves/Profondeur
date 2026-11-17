package fr.projetpo;

import fr.projetpo.carte.*;



public class PSP extends Monstre {
    //PETIT SLIME PIQUANT 

    public PSP() {
        super();
        this.pvmax = 12;
        deck.add(new ChargePSP());
        this.pvnow = this.pvmax;
        this.name="PETIT SLIME PIQUANT";
    }

    public  void action(Monstre a, Hero b){
         
        deck.get(0).effet(a,b);
        a.etat.statutBaisse(a);
    }
}
