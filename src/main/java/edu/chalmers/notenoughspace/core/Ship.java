package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * Created by Vibergf on 25/04/2017.
 */
public class Ship implements Entity {

    /**
     * The distance from the ship to the planet's surface.
     */
    public static final float ALTITUDE = 1.8f;
    public static final float SPEED = 1;
    public static final float ROTATION_SPEED = 2;

    private int energy;
    public Beam beam;

    private PlanetaryInhabitant body;

    public Ship(){
        Bus.getInstance().post(new EntityCreatedEvent(this));

        energy = 100;
        beam = new Beam(this);

    }

    public void update() {
        beam.update();
    }

    public void moveForwards(PlanetaryInhabitant body, float tpf) {
        body.rotateForward(-1 * tpf);
    }

    public void moveBackwards(PlanetaryInhabitant body, float tpf) {
        body.rotateForward(1 * tpf);
    }

    public void moveLeft(PlanetaryInhabitant body, float tpf) {
        body.rotateSideways(1 * tpf);
    }

    public void moveRight(PlanetaryInhabitant body, float tpf) {
        body.rotateSideways(-1 * tpf);
    }

    public void rotateLeft(PlanetaryInhabitant body, float tpf){
        body.rotateModel(2 * tpf);
    }

    public void rotateRight(PlanetaryInhabitant body, float tpf){
        body.rotateModel(-2 * tpf);
    }

    public void toggleBeam(boolean beamActive) {
        beam.setActive(beamActive);
    }

    public PlanetaryInhabitant getPlanetaryInhabitant() {
        return body;
    }

    public void setPlanetaryInhabitant(PlanetaryInhabitant body) {
        this.body = body;
    }
}
