package fr.projetpo.carte;

import fr.projetpo.*;

import java.util.*;

public class EpeeBoomerang   extends Multicible {
    { cout = 1; }
    private String name="épée boomerang (8 de degât à 3 cible au hasard)";
    @Override
    public void effet(Entite a, Entite b){
    }

    public void effetMultiple(Entite a, ArrayList<Entite>b){
        int i=0;
        while(i<3){
            int random=new Random().nextInt(b.size());
            a.faireDegat(a,b.get(random), 8);
            i++;
        }
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
