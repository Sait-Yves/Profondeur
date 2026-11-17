package fr.projetpo;

import fr.projetpo.carte.*;

public class Hexaghost extends Monstre {
    private int cicle2;


    public Hexaghost() {
        super();
        this.pvmax = 250;
        this.pvnow = this.pvmax;
        this.name=" HEXAGHOST ";
        deck.add(new Charge());
        deck.add( new Brule());
        deck.add(new Enflamme());
        deck.add(new Enfer());
        deck.add(new Divise());
    }

    public void chargement() {
        // Aucun effet lors du chargement
    }

    public  void action( Monstre a, Hero b){
        if(cicle==1){
            deck.get(4).effet(a, b);
            cicle=0;
            return;
        }

        switch (cicle2) {
            case 0:
            case 2:
            case 5:
                deck.get(1).effet(a, b);
                break;
            case 1:
            case 4:
                deck.get(0).effet(a, b);
                break;
            case 3:
                deck.get(2).effet(a, b);
                break;
            case 6:
                deck.get(3).effet(a, b);
                break;
            default:
                throw new IllegalStateException("Cycle du boss invalide: " + cicle2);
        }

        cicle2++;
        if (cicle2 == 7) {
            cicle2 = 0;
            cicle = 1;
        }

    }
}
