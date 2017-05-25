package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.powerup.Powerup;

/**
 * Created by Vibergf on 25/05/2017.
 */
public class PowerupCollisionEvent {

    private Powerup powerup;

    public PowerupCollisionEvent(Powerup powerup){
        this.powerup = powerup;
        System.out.println("Powerup collision event posted.");
    }

    public Powerup getPowerup(){
        return powerup;
    }
}
