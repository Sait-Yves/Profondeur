package fr.projetpo.carte;

import fr.projetpo.*;

public class MPM  extends CarteH {
    // Même pas mal
    { cout = 1; }
    private String name=" même pas mal ( +8 blockage et  pioche une carte)";
    @Override
    public void effet(Entite a, Entite b){
        a.getEtat().setBlock(a.getEtat().getBlock()+8);
        if (a instanceof Hero) {
            Hero h = (Hero) a;
            h.piocheHero();
            a.setEnergie(a.getEnergie()-cout);
        }
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
