package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * Created by Vibergf on 25/04/2017.
 */
public class Ship extends Entity {
    /**
     * The distance from the ship to the planet's surface.
     */
    public static final float ALTITUDE = 1.8f;

    private int energy;
    private Beam beam;
    private Storage storage;
    private MovementStrategy mover;

    public Ship(){
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));

        mover = new NoAccelerationStrategy(body);
        energy = 100;
        beam = new Beam(this);
        storage = new Storage();
    }

    public void update(float tpf) {
        beam.update(body);
        mover.move(tpf);
    }

    public void addMoveInput(Movement movement, float tpf) {
        mover.addMoveInput(movement, tpf);
    }

    public void toggleBeam(boolean beamActive) {
        beam.setActive(beamActive);
    }

    public Beam getBeam(){return beam; }

    public Storage getStorage(){return storage; }

    public String getID() { return "ship"; }

    public float getCurrentSpeedX() {
        return mover.getCurrentSpeedX();
    }

    public float getCurrentSpeedY() {
        return mover.getCurrentSpeedY();
    }
}
