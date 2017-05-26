package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;

/**
 * Created by juliaortheden on 2017-05-17.
 */
public class SatelliteCollisionEvent {
    private Satellite satellite;

    public SatelliteCollisionEvent(Satellite satellite){
        this.satellite = satellite;
        System.out.println("Satellite collision event posted");
    }

    public Satellite getSatellite(){
        return satellite;
    }

    public int getDamage() {
        return satellite.getDamage();
    }
}
