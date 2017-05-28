package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.powerup.Powerup;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;

/**
 * Event fired when the ship collides with a power-up object.
 */
public class PowerupCollisionEvent {

    private final Powerup powerup;

    public PowerupCollisionEvent(Powerup powerup){
        this.powerup = powerup;
        System.out.println("PowerupCollisionEvent posted.");
    }


    public Powerup getPowerup(){
        return powerup;
    }

    public void affect(Ship ship){
        powerup.affect(ship);
    }

}
