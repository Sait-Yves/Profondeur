package fr.projetpo.carte;

import fr.projetpo.*;

import java.util.ArrayList;

public class Enchainement  extends Multicible {
    { cout = 1; }
    private String name="Enchainement (8 de degât à toutes les cibles )";
    @Override
    public void effet(Entite a, Entite b){
    }

    public void effetMultiple(Entite a, ArrayList<Entite>b){
        int i=0;
        while(i<b.size()){
            a.faireDegat(a,b.get(i), 8);
            i++;
        }
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}
