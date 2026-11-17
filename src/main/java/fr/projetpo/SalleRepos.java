package fr.projetpo;

import fr.projetpo.carte.*;

import java.util.Scanner;

public class SalleRepos extends Salle {
    
    public void ouverture(Hero a){
        
        System.out.println();
        System.out.println("Bienvenue aventurier choisissez donc ce qu'il vous plaira mais vous ne pouvez avoir que 2 choses faite attention...");
        System.out.println("[1]  la vache sa brille (choix d'une nouvelle carte) coûte 30 coin");
        System.out.println("[2]  tout beau tout propre (retire toute les alteration negatif) coûte 10 coin");
        System.out.println("[3]  moi blesser? (regagne 15 pv) coûte 5 coin");
        System.out.println("[4]  ARRRGH (regagne 45 pv) coûte 7 coin");
        System.out.println("[5]  J'AI VU LA MORT (regagne 70) coûte 10 coin");
        System.out.println("[6]  mon armure est toute sale (gagne 10 de blockage) coûte 10 coin");
        System.out.println("[7]  NO PAIN NO GAIN (gagne 5 de force) coûte 10 coin");
        System.out.println("[8]  ne rien faire");

        Scanner sc= new Scanner(System.in);
        

        int achat=0;
            while(achat<2){
                System.out.println();
                System.out.println("Dans ta poche il reste "+a.getCoin()+" pièces");

                System.out.println("fais ton choix");
                int choisi= sc.nextInt();
                while(choisi<=0|| choisi>8){
                    System.out.println();
                    System.out.println("tu n'as vraiment pas confience en mes createurs hein? je vais le leur dire");
                    choisi= sc.nextInt();
                }
                if(choisi>0|| choisi<=8){
                    if(choisi==1 && a.getCoin()>=30){
                        achat++;
                        a.getDeck().add(recompense());
                        a.setCoin(a.getCoin()-30);
                        
                    }
                    if(choisi==2 && a.getCoin()>=10){
                        achat++;
                        a.getEtat().statutReset();
                        a.setCoin(a.getCoin()-10);
                    }
                    if(choisi==3 && a.getCoin()>=5){
                        achat++;
                        a.soigner(15);
                        a.setCoin(a.getCoin()-5);
                    }
                    if(choisi==4 && a.getCoin()>=7){
                        achat++;
                        a.soigner(45);
                        a.setCoin(a.getCoin()-7);
                    }
                    if(choisi==5 && a.getCoin()>=10){
                        achat++;
                        a.soigner(70);
                        a.setCoin(a.getCoin()-10);
                    }
                    if(choisi==6 && a.getCoin()>=10){
                        achat++;
                        a.getEtat().setBlock(a.getEtat().getBlock()+10);
                        a.setCoin(a.getCoin()-10);
                    }
                    if(choisi==7 && a.getCoin()>=10){
                        achat++;
                        a.getEtat().setForce(a.getEtat().getForce()+5);
                        a.setCoin(a.getCoin()-10);
                    }
                    if(choisi==8)achat=2; 
                    
                    System.out.println();
                    System.out.println("sale pauvre");
            
                }
            }
    }

}
