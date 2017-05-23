package edu.chalmers.notenoughspace.core.entity.ship;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.entity.enemy.Hayfork;
import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;
import edu.chalmers.notenoughspace.core.move.Accelerator;
import edu.chalmers.notenoughspace.core.move.Movement;
import edu.chalmers.notenoughspace.core.move.MovementStrategy;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.HayforkHitEvent;
import edu.chalmers.notenoughspace.event.SatelliteCollisionEvent;

/**
 * Created by Vibergf on 25/04/2017.
 */
public class Ship extends Entity {
    /**
     * The distance from the ship to the planet's surface.
     */
    public static final float ALTITUDE = 1.8f;

    private Health health;
    private float energy;
    private Beam beam;
    private Storage storage;
    private MovementStrategy mover;

    public Ship(){
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));
        Bus.getInstance().register(this);

        mover = new Accelerator(body);
        health = new Health(100);
        energy = 80;
        beam = new Beam(this);
        storage = new Storage();
    }

    public void update(float tpf) {
        beam.update(body, tpf);
        mover.move(tpf);
        expendEnergy(tpf);
    }

    public void addMoveInput(Movement movement, float tpf) {
        mover.addMoveInput(movement, tpf);
    }

    public void toggleBeam(boolean beamActive) {
        beam.setActive(beamActive);
    }

    public Beam getBeam(){return beam; }

    public Storage getStorage(){return storage; }

    public float getHealth() {
        return health.getHealthLevel();
    }

    public float getEnergy() {
        return energy;
    }

    public String getID() { return "ship"; }

    public float getCurrentSpeedX() {
        return mover.getCurrentSpeedX();
    }

    public float getCurrentSpeedY() {
        return mover.getCurrentSpeedY();
    }

    @Subscribe
    public void gotHit(HayforkHitEvent event) {
        int damage = ((Hayfork) event.getHayFork()).getDamage();
        health.increaseHealth(-damage);
        System.out.println(health.toString());
    }

    @Subscribe
    public void satelliteCollision(SatelliteCollisionEvent event) {
        int damage = ((Satellite) event.getSatellite()).getDamage();
        health.increaseHealth(-damage);
        System.out.println(health.toString());
    }

    private void expendEnergy(float tpf) {
        if (beam.isActive()) {
            energy -= tpf * 10;
        } else {
            if (energy < 100) {
                energy += tpf * 5;
            }
        }

        if (energy <= 0) {
            beam.setActive(false);
        }
    }
}
