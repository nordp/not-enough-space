package edu.chalmers.notenoughspace.core;


import com.jme3.math.Vector3f;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.SatelliteCollisionEvent;


public class Satellite extends Entity {


    Satellite() {
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }

    public void update() {
    }


    /**
     * make the satellite throw collision event if it is colliding with the ship
     */

    public void collision() {
        Bus.getInstance().post(new SatelliteCollisionEvent(this));
    }
}



