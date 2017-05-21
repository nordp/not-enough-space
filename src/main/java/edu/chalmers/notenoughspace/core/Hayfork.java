package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.HayforkHitEvent;

import javax.vecmath.Vector3f;

/**
 * Created by Sparven on 2017-05-15.
 */
public class Hayfork extends Entity {

    private final static float THROW_SPEED = 10f;

    private Entity thrower;
    private Vector3f direction;
    private int damage;

    public Hayfork(Entity thrower){
        super(new ZeroGravityStrategy());
        this.thrower = thrower;
        Bus.getInstance().post(new EntityCreatedEvent(this));

        damage = 10;
    }

    public void update(PlanetaryInhabitant ship, float tpf) {
        if (direction == null) {
            Vector3f myPosition = body.getWorldTranslation();
            Vector3f shipPosition = ship.getWorldTranslation();
            body.setDirection(shipPosition);    //Aims the hayfork towards the ship
            shipPosition.sub(myPosition);
            shipPosition.normalize();
            shipPosition.scale(0.1f);
            direction = shipPosition;   //The direction the spear will be moving in

        }

        body.move(direction);
    }

    public void hitSomething() {
        Vector3f pierceIntoVector = new Vector3f(direction.x, direction.y, direction.z);
        pierceIntoVector.scale(3.2f);
        body.move(pierceIntoVector);
        Bus.getInstance().post(new HayforkHitEvent(this));
    }

    public Entity getThrower() {
        return thrower;
    }

    public int getDamage() {
        return damage;
    }

    public Vector3f getDirection() {
        return direction;
    }
}
