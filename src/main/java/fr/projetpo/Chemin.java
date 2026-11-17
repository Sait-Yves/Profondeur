package fr.projetpo;

import fr.projetpo.carte.*;

import java.util.*;
public class Chemin {
    private ArrayList<Salle> route;
    private SalleCombat facile = new SalleCombat();
    private SalleCombat moyen = new SalleCombat();
    private SalleCombat dur = new SalleCombat();
    private SalleRepos calme= new SalleRepos();
    private SalleChance chance= new SalleChance();

    public Chemin(){
        this.route= new ArrayList<>();
        //salle facile
        this.facile.getGang().add(new PSA());
        this.facile.getGang().add(new PSA());
        this.facile.getGang().add(new PSP());
        this.facile.getGang().add(new PSP());
        this.facile.getGang().add(new PSP());
        this.facile.setCoin(3);

        //salle moyenne
        this.moyen.getGang().add(new SP());
        this.moyen.getGang().add(new SP());
        this.moyen.getGang().add(new SA());
        this.moyen.getGang().add(new Machouilleur());
        this.moyen.setCoin(5);

        //salle dur
        this.dur.getGang().add(new SA());
        this.dur.getGang().add(new SA());
        this.dur.getGang().add(new Machouilleur());
        this.dur.getGang().add(new Cultiste());
        this.dur.getGang().add(new Cultiste());
        this.dur.setCoin(8);
    }

    public void easy(){
        route.clear();

        route.add(facile.clone());
        route.add(facile.clone());
        route.add(facile.clone());

        route.add(calme.clone());
        route.add(chance.clone());
        route.add(facile.clone());
        route.add(moyen.clone());

        route.add(calme.clone());
        route.add(moyen.clone());
        route.add(chance.clone());
        route.add(moyen.clone());
    }

    public void mid(){
        route.clear();

        route.add(facile.clone());
        route.add(facile.clone());
        route.add(chance.clone());
        route.add(moyen.clone());

        route.add(calme.clone());
        route.add(moyen.clone());
        route.add(moyen.clone());
        route.add(moyen.clone());
        route.add(chance.clone());

        route.add(calme.clone());
        route.add(dur.clone());
        route.add(dur.clone());
        route.add(new SalleBoss());
    }

    public void hard(){
        route.clear();

        route.add(moyen.clone());
        route.add(moyen.clone());
        route.add(chance.clone());
        route.add(moyen.clone());
        

        route.add(calme.clone());
        route.add(chance.clone());
        route.add(moyen.clone());
        route.add(dur.clone());
        route.add(moyen.clone());
        route.add(dur.clone());
        route.add(chance.clone());

        route.add(calme.clone());
        route.add(chance.clone());
        route.add(dur.clone());
        route.add(dur.clone());
        route.add(dur.clone());

        
        route.add(calme.clone());
        route.add(new SalleBoss());
    }
    
    public ArrayList<Salle> getRoute() {
        return route;
    }
}
