package edu.chalmers.notenoughspace.core.entity.powerup;

import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.PowerupCollisionEvent;

/**
 * Entity refilling the ship's resources in different ways when collected.
 */
public abstract class Powerup extends Entity {

    public Powerup() {
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }


    protected void onPlanetaryInhabitantAttached(){
        Entity.randomizeDirection(body);
    }

    public void collision() {
        Bus.getInstance().post(new PowerupCollisionEvent(this));
    }

    public abstract void affect(Ship ship);

}
