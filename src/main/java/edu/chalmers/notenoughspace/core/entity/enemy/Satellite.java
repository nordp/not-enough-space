package edu.chalmers.notenoughspace.core.entity.enemy;


import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.SatelliteCollisionEvent;

/**
 * Entity orbiting the planet. Can collide with the
 * ship, thereby causing a reduction of the ship's health.
 */
public class Satellite extends Entity {

    private final static int DAMAGE = 25;

    public Satellite() {
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }


    public void update(float tpf) {
        moveInOrbit(tpf);   //TODO: Implement movement strategy for this and for power-up's movement.
    }

    protected void onPlanetaryInhabitantAttached(){
        randomizeDirection();
    }

    public void collision() {
        Bus.getInstance().post(new SatelliteCollisionEvent(this));
    }

    public int getDamage() {
        return DAMAGE;
    }


    private void moveInOrbit(float tpf) {
        body.rotateForward(0.35f * tpf);
    }

}



