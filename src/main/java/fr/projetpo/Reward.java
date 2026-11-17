package fr.projetpo;

import fr.projetpo.carte.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reward implements Cloneable{
    private static final double PROBABILITE_COMMUNE = 0.6;
    private static final double PROBABILITE_NON_COMMUNE = 0.37;
    private static final double PROBABILITE_RARE = 0.03;
    private Deck reward;

    public Deck getReward() {
        return reward;
    }

    public Reward(){
        reward= new Deck();
        reward.add(genererRecompense());
        reward.add(genererRecompense());
        reward.add(genererRecompense());
    }

    // Méthode pour générer une récompense
    public static Carte genererRecompense() {
        Random random = new Random();
        double rand = random.nextDouble();

        // Choix de la rareté
        if (rand < PROBABILITE_COMMUNE) {
            return genererCarteRarete("Commune");
        } else if (rand < PROBABILITE_COMMUNE + PROBABILITE_NON_COMMUNE) {
            return genererCarteRarete("Non-Commune");
        } else {
            return genererCarteRarete("Rare");
        }
    }

    // Méthode pour générer une carte selon la rareté
    private static Carte genererCarteRarete(String rarete) {
        List<Carte> cartesDisponibles = getCartesDisponibles(rarete);
        Random random = new Random();
        int indexCarte = random.nextInt(cartesDisponibles.size());
        return cartesDisponibles.get(indexCarte).clone(); // Cloner pour éviter de modifier la carte originale
    }

    // Méthode pour obtenir toutes les cartes disponibles d'une rareté
    private static List<Carte> getCartesDisponibles(String rarete) {
        List<Carte> cartes = new ArrayList<>();

        
        if (rarete.equals("Commune")) {
            cartes.add(new Frappe());
            cartes.add(new Defense());
            cartes.add(new MPM());
            cartes.add(new VagueFer());
            cartes.add(new FrappePommeau());
            cartes.add(new FrappeDouble());
            cartes.add(new Enchainement());
            cartes.add(new EpeeBoomerang());
            cartes.add(new Manchette());
            cartes.add(new Plaquage());
        
        } else if (rarete.equals("Non-Commune")) {
            cartes.add(new Saignee());
            cartes.add(new Hemokinesie());
            cartes.add(new Uppercut());
            cartes.add(new VoleeCoups());
            cartes.add(new VoirRouge());
            cartes.add(new Enflammer());
            cartes.add(new Desarmement());
            cartes.add(new OndeChoc());
            cartes.add(new Tenacite());
        
        } else if (rarete.equals("Rare")) {
            cartes.add(new Gourdin());
            cartes.add(new Invincible());
            cartes.add(new Offrande());
            cartes.add(new FormeDemoniaque());
        }

        return cartes;
    }

    @Override
    public Reward clone() {
        try {
            Reward clone = (Reward) super.clone();
            clone.reward = this.reward.clone(); // Assume that Deck is also cloneable
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Impossible de cloner les récompenses", e);
        }
    }


}
