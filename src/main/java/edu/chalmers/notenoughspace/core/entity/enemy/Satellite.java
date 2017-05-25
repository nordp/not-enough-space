package edu.chalmers.notenoughspace.core.entity.enemy;


import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.SatelliteCollisionEvent;


public class Satellite extends Entity {

    int damage;

    public Satellite() {
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));

        damage = 25;
    }

    protected void onPlanetaryInhabitantAttached(){
        Entity.randomizeDirection(body);
    }

    public void update() {
    }


    /**
     * make the satellite throw collision event if it is colliding with the ship
     */

    public void collision() {
        Bus.getInstance().post(new SatelliteCollisionEvent(this));
    }

    public int getDamage() {
        return damage;
    }
}



