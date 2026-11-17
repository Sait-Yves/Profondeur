package fr.projetpo;

import fr.projetpo.carte.*;


import java.util.*;
public class Deck implements Cloneable{
    
    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private List<Carte> deck;

    public Deck() {
        this.deck = new ArrayList<>();
    }

    public Carte pioche(){
        if (deck.isEmpty()) {
            return null;
        }
        return deck.remove(new Random().nextInt(deck.size()));
    }

    public Deck melange( Deck a, Deck b){
        a.deck.addAll(b.deck);
        b.vider();
        return a;
    }

    public void add(Carte c){
        deck.add(c);
    } 

    public void remove(Carte c){
        deck.remove(c);
    }

    public void vider(){
        deck.clear();
    }

    public boolean empty(){
        return deck.isEmpty();
    }
     public Carte get(int a){
        return deck.get(a);
     }

     public int size(){
        return deck.size();
     }

     public void alldeck(){
        int x=0;
        System.out.println("Dans votre "+name+" il y a:\n");
        while (x< deck.size()){
            System.out.println("En position "+x);
            System.out.println(deck.get(x).presentation()+"\n");
            x++;
        }
     }


    public boolean contains(Carte c){
        return deck.contains(c);
    }

    public List<Carte> getCartes() {
        return Collections.unmodifiableList(deck);
    }

     @Override
    public Deck clone() {
        try {
            Deck clone = (Deck) super.clone();
            // Cloner les cartes dans le deck
            clone.deck = new ArrayList<>();
            for (Carte carte : deck) {
                clone.deck.add(carte.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Impossible de cloner le deck", e);
        }
    }
}
