package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;

/**
 * Event fired when the ship collides with a satellite.
 */
public class SatelliteCollisionEvent {

    private Satellite satellite;

    public SatelliteCollisionEvent(Satellite satellite){
        this.satellite = satellite;
        System.out.println("SatelliteCollisionEvent posted.");
    }


    public Satellite getSatellite(){
        return satellite;
    }

    public int getDamage() {
        return satellite.getDamage();
    }

}
