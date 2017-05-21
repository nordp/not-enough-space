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
    public static final float ROTATION_SPEED = 2;
    
    private final float MAX_SPEED = 40;
    private final float DECELERATION_RATE = 25; //The higher the quicker the ship slows down
    private final float ACCELERATION_RATE = 40;


    private float currentSpeedX;
    private float currentSpeedY;
    private int energy;
    private Beam beam;
    private Storage storage;


    public Ship(){
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));

        currentSpeedX = 0;
        currentSpeedY = 0;
        energy = 100;
        beam = new Beam(this);
        storage = new Storage();
    }

    public void update(float tpf) {
        beam.update(body);
        move(tpf);
    }

    private void move(float tpf) {
        moveForwards(tpf);
        moveLeft(tpf);

        System.out.println(currentSpeedY);
    }

    public void accelerateForwards(float tpf) {
        currentSpeedY += tpf * ACCELERATION_RATE * 1.0/400;
        adjustYSpeedIfNecessary();
    }
    
    public void accelerateBackwards(float tpf) {
        currentSpeedY -= tpf * ACCELERATION_RATE * 1.0/400;
        adjustYSpeedIfNecessary();
    }
    
    public void accelerateLeft(float tpf) {
        currentSpeedX += tpf * ACCELERATION_RATE * 1.0/400;
        adjustXSpeedIfNecessary();
    }
    
    public void accelerateRight(float tpf) {
        currentSpeedX -= tpf * ACCELERATION_RATE * 1.0/400;
        adjustXSpeedIfNecessary();
    }
    
    //Helper
    private void adjustYSpeedIfNecessary() {
        if (currentSpeedY > MAX_SPEED/1000) {
            currentSpeedY = MAX_SPEED/1000;
        } else if (currentSpeedY < -MAX_SPEED/1000) {
            currentSpeedY = -MAX_SPEED/1000;
        }

        if (Math.abs(currentSpeedY) < 0.001) {
            currentSpeedY = 0;
        }
    }

    //Helper
    private void adjustXSpeedIfNecessary() {
        if (currentSpeedX > MAX_SPEED/1000) {
            currentSpeedX = MAX_SPEED/1000;
        } else if (currentSpeedX < -MAX_SPEED/1000) {
            currentSpeedX = -MAX_SPEED/1000;
        }

        if (Math.abs(currentSpeedX) < 0.001) {
            currentSpeedX = 0;
        }
    }

    private void moveForwards(float tpf) {
        body.rotateForward(-1 * currentSpeedY * tpf * 20);

        if (currentSpeedY > 0) {
            currentSpeedY -= tpf * DECELERATION_RATE * 1.0/400;
        } else if (currentSpeedY < 0) {
            currentSpeedY += tpf * DECELERATION_RATE * 1.0/400;
        }
    }

    private void moveLeft(float tpf) {
        body.rotateSideways(1 * currentSpeedX * tpf * 20);

        if (currentSpeedX > 0) {
            currentSpeedX -= tpf * DECELERATION_RATE * 1.0/400;
        } else if (currentSpeedX < 0) {
            currentSpeedX += tpf * DECELERATION_RATE * 1.0/400;
        }
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
