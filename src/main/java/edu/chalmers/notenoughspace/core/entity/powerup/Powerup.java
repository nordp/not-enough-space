package edu.chalmers.notenoughspace.core.entity.powerup;

import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.PowerupCollisionEvent;

/**
 * Created by Vibergf on 25/05/2017.
 */
public abstract class Powerup extends Entity {

    public Powerup() {
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }

    public void collision() {
        Bus.getInstance().post(new PowerupCollisionEvent(this));
    }

    public abstract void affect(Ship ship);
}
