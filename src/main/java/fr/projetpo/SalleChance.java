package fr.projetpo;

import fr.projetpo.carte.*;

import java.util.*;
public class SalleChance extends Salle {
    @Override
    public void ouverture(Hero a){
        int chance= new Random().nextInt(10);
        if (chance<5)a.getEtat().setFaible(a.getEtat().getFaible()+2);
        if (chance<5)a.getEtat().setForce(a.getEtat().getForce()+3);
    }
}
