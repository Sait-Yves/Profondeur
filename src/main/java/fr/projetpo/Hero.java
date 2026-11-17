package fr.projetpo;

import fr.projetpo.carte.*;

import java.util.ArrayList;

public class Hero extends Entite {
    private Deck main;
    private Deck defausse;
    private Deck exile;
    private int demoniaque;
    private int coin;
    private Salle salle;

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getDemoniaque() {
        return demoniaque;
    }

    public void setDemoniaque(int demoniaque) {
        this.demoniaque = demoniaque;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Hero(String name) {
        super();
        this.name = name == null || name.trim().isEmpty() ? "Aventurier" : name.trim();
        this.pvmax = 70;
        this.exile= new Deck();
        this.main= new Deck();
        main.setName("main");
        this.defausse= new Deck();
        defausse.setName("defausse");
        deck.add(new Frappe());
        deck.add(new Frappe());
        deck.add(new Frappe());
        deck.add(new Frappe());
        deck.add(new Frappe());
        deck.add(new Defense());
        deck.add(new Defense());
        deck.add(new Defense());
        deck.add(new Defense());
        deck.add(new Heurt());
        this.energie=3;
        this.pvnow = this.pvmax;
    }

    public void piocheHero(){
        Carte res=deck.pioche();
        if (res == null) {
            if (defausse.empty()) {
                return;
            }
            deck.melange(deck, defausse);
            res = deck.pioche();
        }
        if (res == null) {
            return;
        }
        if(res instanceof Pollution ||res instanceof Plaie){
            etat.setFaible(etat.getFaible()+1);// debuff
        }
        main.add(res);
    }

    public void soigner(int soins){
        if (soins > 0) {
            setPvnow(pvnow + soins);
        }
    }

    public void action(Carte z, Hero a, Entite b){
        if (z == null || !(z instanceof CarteH) || z.getCout() > energie || !main.contains(z)) {
            return;
        }
        etat.setForce(etat.getForce() + demoniaque);
        if(z instanceof Multicible && salle != null){
            ArrayList<Entite> livingEnemies = new ArrayList<>();
            for (Entite enemy : salle.getGang()) {
                if (enemy.alive()) {
                    livingEnemies.add(enemy);
                }
            }
            ((Multicible)z).effetMultiple(a, livingEnemies);
        }
        z.effet(a,b);
        main.remove(z);
        if(z.getExilable()==1)exile.add(z);
        else defausse.add(z);
        a.etat.statutBaisse(a);
    }


    

    

    public Deck getMain() {
        return main;
    }

    public void setMain(Deck main) {
        this.main = main;
    }

    public Deck getDefausse() {
        return defausse;
    }

    public Deck getExile() {
        return exile;
    }

}