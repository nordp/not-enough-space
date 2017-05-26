package edu.chalmers.notenoughspace.core.entity.ship;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.entity.powerup.Powerup;
import edu.chalmers.notenoughspace.core.move.*;
import edu.chalmers.notenoughspace.event.*;

/**
 * The UFO that the player controls. Responsible for being moved by the player
 * and activating/deactivating the beam. Keeps track of its own health and
 * energy levels, as well as a storage.
 */
public class Ship extends Entity {

    public static final float ALTITUDE = 1.8f;  //Distance to the planet's surface.

    private Health health;
    private Energy energy;
    private Beam beam;
    private Storage storage;
    private MovementStrategy mover;

    public Ship(){
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));
        Bus.getInstance().register(this);

        health = new Health(100, 100);
        energy = new Energy(100, 100,  5);
        beam = new Beam(this);
        storage = new Storage();
    }


    @Override
    protected void onPlanetaryInhabitantAttached(){
        mover = new Accelerator();  //TODO: Explain why this is added here.
    }

    public void update(float tpf) {
        beam.update(body, tpf);
        mover.move(body, tpf);
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

    public void toggleBeam(boolean active) {
        beam.setActive(active);
    }

    public Beam getBeam(){ return beam; }

    public Storage getStorage(){ return storage; }

    public float getHealth() {
        return health.getCurrentHealthLevel();
    }

    public void modifyHealth(int changeInHealth) {
        health.modifyHealth(changeInHealth);
    }

    public float getEnergy() {
        return energy.getCurrentEnergyLevel();
    }

    public void modifyEnergy(float changeInEnergy) {
        energy.modifyEnergy(changeInEnergy);
    }

    public String getID() { return "ship"; }

    public float getCurrentXSpeed() {
        return mover.getCurrentXSpeed();
    }

    public float getCurrentYSpeed() {
        return mover.getCurrentYSpeed();
    }

    public int getScore() {
        return storage.getScore();
    }

    @Subscribe
    public void hayforkCollision(HayforkCollisionEvent event) {
        int damage = event.getDamage();
        health.modifyHealth(-damage);
    }

    @Subscribe
    public void satelliteCollision(SatelliteCollisionEvent event) {
        int damage = event.getDamage();
        health.modifyHealth(-damage);
    }

    @Subscribe
    public void powerupCollision(PowerupCollisionEvent event) {
        Powerup powerup = event.getPowerup();
        powerup.affect(this);
    }

    @Subscribe
    public void energyEmpty(EnergyEmptyEvent event) {
        toggleBeam(false);
    }


    private void updateEnergy(float tpf) {
        if (beam.isActive()) {
            float expendedEnergy = Beam.getEnergyCost() * tpf;
            energy.modifyEnergy(-expendedEnergy);
        } else {
            energy.regenerate(tpf);
        }
    }

}
