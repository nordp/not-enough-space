package edu.chalmers.notenoughspace.core.entity.ship;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.entity.enemy.Hayfork;
import edu.chalmers.notenoughspace.core.move.Accelerator;
import edu.chalmers.notenoughspace.core.move.Movement;
import edu.chalmers.notenoughspace.core.move.MovementStrategy;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.*;

/**
 * Created by Vibergf on 25/04/2017.
 */
public class Ship extends Entity {
    /**
     * The distance from the ship to the planet's surface.
     */
    public static final float ALTITUDE = 1.8f;

    private Health health;
    private Energy energy;
    private Beam beam;
    private Storage storage;
    private MovementStrategy mover;

    public Ship(){
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));
        Bus.getInstance().register(this);

        mover = new Accelerator(body);
        health = new Health(100, 100);
        energy = new Energy(100, 100,  5);
        beam = new Beam(this);
        storage = new Storage();
    }

    public void update(float tpf) {
        beam.update(body, tpf);
        mover.move(tpf);
        updateEnergy(tpf);
    }

    public void cleanup(){
        Bus.getInstance().unregister(beam);
        Bus.getInstance().post(new EntityRemovedEvent(beam));
        Bus.getInstance().unregister(this);
        Bus.getInstance().post(new EntityRemovedEvent(this));
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

    public void modifyHealth(int dHealth) {
        health.modifyHealth(dHealth);
    }

    public float getEnergy() {
        return energy.getEnergyLevel();
    }

    public void modifyEnergy(float dEnergy) {
        energy.modifyEnergy(dEnergy);
    }

    public String getID() { return "ship"; }

    public float getCurrentSpeedX() {
        return mover.getCurrentSpeedX();
    }

    public float getCurrentSpeedY() {
        return mover.getCurrentSpeedY();
    }

    @Subscribe
    public void hayforkCollision(HayforkCollisionEvent event) {
        int damage = ((Hayfork) event.getHayFork()).getDamage();
        health.modifyHealth(-damage);
        System.out.println(health.toString());
    }

    @Subscribe
    public void satelliteCollision(SatelliteCollisionEvent event) {
        int damage = event.getSatellite().getDamage();
        health.modifyHealth(-damage);
        System.out.println(health.toString());
    }

    @Subscribe
    public void powerupCollision(PowerupCollisionEvent event) {
        event.getPowerup().affect(this);
    }

    @Subscribe
    public void energyEmpty(EnergyEmptyEvent event) {
        toggleBeam(false);
    }

    private void updateEnergy(float tpf) {
        if (beam.isActive()) {
            energy.modifyEnergy(-Beam.ENERGY_COST * tpf);
        } else {
            energy.regenerate(tpf);
        }

//        if (energy <= 5) {
//            beam.setActive(false);
//        }
    }
}
