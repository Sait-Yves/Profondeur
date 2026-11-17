package fr.projetpo.carte;

import fr.projetpo.*;

import java.util.ArrayList;

public abstract class Multicible extends CarteH {
    public abstract void effet(Entite a, Entite b);

    public abstract void effetMultiple(Entite a, ArrayList<Entite>b);
}
