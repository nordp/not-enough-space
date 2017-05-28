package edu.chalmers.notenoughspace.core.entity.enemy;

import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.HayforkCollisionEvent;

import javax.vecmath.Vector3f;

/**
 * Entity thrown by the farmer. Can collide with and get stuck at the ship,
 * thereby causing a reduction of the ship's health.
 */
public class Hayfork extends Entity {

    private final static float THROW_SPEED = 8f;
    private final static int DAMAGE = 10;

    private final Entity thrower;
    private Vector3f direction;

    public Hayfork(Entity thrower){
        super(new ZeroGravityStrategy());
        this.thrower = thrower;

        Bus.getInstance().post(new EntityCreatedEvent(this));
    }


    public void update(PlanetaryInhabitant ship, float tpf) {
        if (direction == null) {
            setDirectionTowards(ship);
        }

        body.move(distanceToMove(tpf));
    }

    private void setDirectionTowards(PlanetaryInhabitant ship) {
        Vector3f myPosition = body.getPosition();
        Vector3f shipPosition = ship.getPosition();
        body.setDirection(shipPosition);

        shipPosition.sub(myPosition);   //Results in vector directed directly towards the ship.
        shipPosition.normalize();
        shipPosition.scale(THROW_SPEED);
        direction = shipPosition;
    }

    public void hitSomething() {
        Vector3f pierceIntoVector = new Vector3f(direction.x, direction.y, direction.z);
        pierceIntoVector.normalize();
        pierceIntoVector.scale(0.32f);
        body.move(pierceIntoVector);

        Bus.getInstance().post(new HayforkCollisionEvent(getID(), getDamage()));
    }

    public Entity getThrower() {
        return thrower;
    }

    public int getDamage() {
        return DAMAGE;
    }

    public Vector3f getDirection() {
        return direction;
    }


    private Vector3f distanceToMove(float tpf) {
        Vector3f distanceToMove = new Vector3f();
        distanceToMove.set(direction);
        distanceToMove.scale(tpf);
        return distanceToMove;
    }

}
