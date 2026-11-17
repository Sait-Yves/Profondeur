package fr.projetpo.carte;

import fr.projetpo.*;

public class Rituel extends CarteM{
    @Override
    public void effet(Entite a, Entite b){
        if (a instanceof Cultiste) {
            Cultiste cultiste = (Cultiste) a;
            cultiste.setRituel(cultiste.getRituel()+3);
        }
    }
}
