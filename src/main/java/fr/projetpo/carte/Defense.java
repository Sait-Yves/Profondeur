package fr.projetpo.carte;

import fr.projetpo.*;



public class Defense  extends CarteH {
    { cout = 1; }
    private String name="defence (+ 5 de blockage)";
    @Override
    public void effet(Entite a, Entite b){
        a.getEtat().setBlock(a.getEtat().getBlock()+5);
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
