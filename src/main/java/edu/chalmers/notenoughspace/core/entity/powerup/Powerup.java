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

    Powerup() {
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }


    public void update(float tpf) {
        moveInOrbit(tpf);   //TODO: Implement movement strategy for this and for satellite's movement.
        rotateOnSpot(tpf);
    }

    protected void onPlanetaryInhabitantAttached(){
        randomizeDirection();
    }

    public void collision() {
        Bus.getInstance().post(new PowerupCollisionEvent(this));
    }

    public abstract void affect(Ship ship);


    private void moveInOrbit(float tpf) {
        body.rotateForward(0.25f * tpf);
    }

    private void rotateOnSpot(float tpf) {
        body.rotateAroundOwnCenter(1 * tpf, 1.2f * tpf, 0.8f * tpf);
    }

}
