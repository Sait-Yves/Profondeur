package fr.projetpo.carte;

import fr.projetpo.*;

public class Offrande extends CarteH {
    { cout = 0; }
    private String name = "Offrande (Perds 6 points de vie, gagne 2 points d’énergie, puis pioche 3 cartes)";
    { exilable = 1; }
    @Override
    public void effet(Entite a, Entite b) {
        a.setPvnow(a.getPvnow()-6);;
        a.setEnergie(a.getEnergie() + 2);
        if(a instanceof Hero){
            Hero c= (Hero)a;
            for (int i = 0; i < 3; i++) {
                c.piocheHero();
            }
        }
        a.setEnergie(a.getEnergie()-cout);
    }
    @Override
    public String presentation(){
        return "La carte "+name+" qui a un coût de "+cout;
    }
}

