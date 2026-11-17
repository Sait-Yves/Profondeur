package fr.projetpo;

import fr.projetpo.carte.*;

import java.util.ArrayList;

public class SalleBoss extends SalleCombat {
    
    public SalleBoss(){
        this.gang= new ArrayList<>();
        this.reward= new Reward();
        gang.add(new Hexaghost());
    }
}
