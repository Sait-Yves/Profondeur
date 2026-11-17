package fr.projetpo;

import fr.projetpo.carte.*;

import java.util.*;
import java.util.stream.Collectors;
public class SalleCombat extends Salle {
    
    
    @Override
    public void ouverture(Hero a){
        combat(a);
        a.setDeck(a.getDeck().melange(a.getDeck().melange(a.getDeck(),a.getExile()), a.getDefausse()));
        

        if(a.alive()){
            a.getDeck().add(recompense());
            System.out.println();
            System.out.println(" si tu n'es pas mort à c'est déjà pas mal!! Allez salle suivante");
            System.out.println();
            System.out.println("vu que tu t'en ai sortie voila un peu d'argent de poche achète toi une glace avec");
            a.setCoin(a.getCoin()+coin);
        }

        else{
            System.out.println();
            System.out.println(" attends tu est vraiment mort? Pff je sais même pas à quoi je m'attendais ");
        } 

        
    }

    public void combat(Hero a){
        a.piocheHero();
        a.piocheHero();
        a.piocheHero();
        a.piocheHero();
        a.piocheHero();
        System.out.println("alors normalement tu as t'es carte c'est partie");
        Scanner sc = new Scanner(System.in);

        boucleHero(a, sc);

        List<Entite> alive= getGang().stream().filter(x-> x.alive()).collect(Collectors.toList());
        while (a.alive() && alive.size() > 0) {
            boucleMonstre(alive, a);
            if(a.alive())boucleHero(a, sc);
             alive = alive.stream().filter(x-> x.alive()).collect(Collectors.toList());
        }

    }

    public void boucleHero(Hero a, Scanner sc){
        a.setEnergie(3);
        List<Entite> alive= getGang().stream().filter(x-> x.alive()).collect(Collectors.toList());

        
        while(a.getEnergie()>0){
        //choix carte
            System.out.println();
            a.getMain().alldeck();

            System.out.println("choisis la carte que tu veux jouer et si tu ne veux rien faire tape 99 entrez puis tu devras le taper une 2ème fois");
            System.out.println("le premier choix c'est le numero 0 compris? Va pas faire n'importe quoi! ");
            
            int carte= sc.nextInt();

            // while (!sc.hasNextInt()) {
            //     System.out.println();
            //     System.out.println("Veuillez entrer un nombre entier.");
            //     sc.next(); // consomme l'entrée invalide
            // }

            while (carte>= a.getMain().size()&& carte!=99|| carte<0){
                    System.out.println();
                    System.out.println("hmm, est ce que tu essairai de nous faire crache? dire que je pensais enfin avoir un joueur sympa");
                    carte= sc.nextInt();
                    
            }

            if(carte == 99){
                a.setEnergie(0);
                System.out.println();
                System.out.println("il te reste "+a.getEnergie()+" d'énergie");
                alive = alive.stream().filter(x-> x.alive()).collect(Collectors.toList());
                break;
            } 

            else{
                
        //choix cible
                stillAlive(alive);
                System.out.println();
                System.out.println("choisis une cible");
                int cible= sc.nextInt();
                while (cible>= alive.size()|| cible<0){
                    System.out.println();
                    System.out.println("arrête ça, moi ça ne me fait pas rire   >:-(  ");
                    cible= sc.nextInt();
                }
            
        //action
                a.action(a.getMain().get(carte), a, alive.get(cible));
                
            }

            

            System.out.println();
            System.out.println("il te reste "+a.getEnergie()+" d'énergie");
            alive = alive.stream().filter(x-> x.alive()).collect(Collectors.toList());
        }
        
        int i=0;
        while(i<a.getDefausse().size()){
            if(a.getDefausse().get(i) instanceof Brulure){
                a.getDefausse().remove(a.getDefausse().get(i));
                a.setPvnow(a.getPvnow()-2);// j'ai l'impression que brulure devrait directement attaquer les pv du hero
            } else {
                i++;
            }
        }
        
    }

    public void boucleMonstre(List<Entite> alive, Hero a){
        int num = 0;
            while (num < alive.size()) {
                if (alive.get(num) instanceof Monstre) {
                    Monstre monstre = (Monstre) alive.get(num);
                    monstre.action(monstre, a);
                    System.out.println();
                    a.presentation();
                }
                num++; 
            }
    }


    // affiche tous les monstre de la salle
    public void stillAlive(List<Entite>alive){
        int x=0;
        while (x< alive.size()){
            System.out.println("En position "+x);
            System.out.println(alive.get(x).presentation());
            x++;
        }
    }
}
