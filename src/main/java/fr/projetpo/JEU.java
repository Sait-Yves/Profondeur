package fr.projetpo;

import fr.projetpo.carte.*;

import java.util.Scanner;

public class JEU {
    public void lancement(){
        System.out.println();
        System.out.println(" Alors bienvenue chez moi apparement je suis sensé m'occuper de toi cette fois, heuu je sais pas quoi dire bonne chance ");
        System.out.println("ah ouais j'ai oublier c'est quoi ton nom à toi sebastien? Thomas? à je sais Alexandre");
        
        
        Scanner sc = new Scanner(System.in);
        String nom = sc.nextLine();

        System.out.println();
        System.out.println("bon c'est ta première fois? bref repond pas sa m'intérressent pas ");
        System.out.println("mais comme je suis sympa je te laisse choisir ta difficulté en gros 1 = facile, 2 = moyen et 3 = dure");
        int difficulte = sc.nextInt();
        
        System.out.println();
        if(difficulte==1)System.out.println("attends serieux? allez quoi tu peux faire mieux que ça");
        if(difficulte==2)System.out.println("hmm alors c'est comme ça que tu te vois dans la vie");
        if(difficulte==3)System.out.println("allez la ça va être intéressant à voir!! Pour la petite info c'est tout sauf équilibrée ");
        
        jeu(nom, difficulte);

    }

    private void jeu(String nom, int difficulte){
        Hero hero = new Hero(nom);
        System.out.println();
        hero.presentation();
        
        int position = 0;
        Chemin donjon= new Chemin();

        if(difficulte==1) {
            donjon.easy();
        }

        if(difficulte==2) {
            donjon.mid();
        }

        if(difficulte==3) {
            donjon.hard();
        }



        while(hero.alive() && position < donjon.getRoute().size()){
            hero.setSalle( donjon.getRoute().get(position));
            donjon.getRoute().get(position).ouverture(hero);
            position++;
        }
        if(hero.alive()){
            System.out.println();
            System.out.println("attends tu as réussis? Noooon j'y crois pas?!");
            System.out.println(" je pense que c'est ta plus belle réussite de l'année tu devrais prendre une photo appeler ta famille et tes amis");
            System.out.println("et avant que tu t'en aille tu as mes plus sincère félicitation");
        }
    }
}
