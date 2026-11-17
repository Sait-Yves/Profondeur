package fr.projetpo;

import fr.projetpo.carte.*;

import java.util.*;
public abstract class Salle implements Cloneable {
    protected Reward reward;
    protected int coin;

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public ArrayList<Entite> getGang() {
        return gang;
    }

    public Deck getRewardDeck() {
        return reward.getReward();
    }

    protected ArrayList <Entite> gang;

    public Salle(){
        this.gang= new ArrayList<>();
        this.reward= new Reward();
    }
    

    public Carte recompense(){
        
        reward.getReward().alldeck();
        Scanner sc= new Scanner(System.in);
        System.out.println();
        System.out.println(" Tu as reussis à vider la salle alors tu as droit à un petit quelque chose");
            
        int carte= sc.nextInt();
        while (carte>= reward.getReward().size()|| carte<0){
            System.out.println();
            System.out.println("hmm, est ce que tu essairai de me faire nous crache? dire que je pensais enfin être tomber sur quelqu'un de sympa");
            carte= sc.nextInt();
        }

        return reward.getReward().get(carte);
    }

    public abstract void ouverture(Hero a);

    @Override
    public Salle clone() {
        try {
            Salle salleClone = (Salle) super.clone();

            // Copier le gang
            salleClone.gang = new ArrayList<>(this.gang.size());
            for (Entite entite : this.gang) {
                salleClone.gang.add(entite.clone());
            }

            // Vous devez également cloner l'objet Reward s'il est clonable
            salleClone.reward = this.reward.clone();

            return salleClone;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Impossible de cloner la salle", e);
        }
    }

}
