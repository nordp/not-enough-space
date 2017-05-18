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
    public static final float SPEED = 1;
    public static final float ROTATION_SPEED = 2;

    private int energy;
    private Beam beam;
    private Storage storage;


    public Ship(){
        Bus.getInstance().post(new EntityCreatedEvent(this));

        energy = 100;
        beam = new Beam(this);
        storage = new Storage();
    }

    public void update() {
        beam.update();
    }

    public void moveForwards(float tpf) {
        body.rotateForward(-1 * tpf);
    }

    public void moveBackwards(float tpf) {
        body.rotateForward(1 * tpf);
    }

    public void moveLeft(float tpf) {
        body.rotateSideways(1 * tpf);
    }

    public void moveRight(float tpf) {
        body.rotateSideways(-1 * tpf);
    }

    public void rotateLeft(float tpf){
        body.rotateModel(2 * tpf);
    }

    public void rotateRight(float tpf){
        body.rotateModel(-2 * tpf);
    }

    public void toggleBeam(boolean beamActive) {
        beam.setActive(beamActive);
    }

    public Beam getBeam(){return beam; }

    public Storage getStorage(){return storage; }

    public String getID() { return "ship"; }
}
