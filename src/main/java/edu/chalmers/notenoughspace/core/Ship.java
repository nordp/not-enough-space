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
    
    private final float MAX_ROTATION_SPEED = 40;
    private final float ROTATION_ACCELERATION_RATE = 200;
    private final float ROTATION_DECELERATION_RATE = 200;
    
    private final float MAX_SPEED = 40;
    private final float DECELERATION_RATE = 45; //The higher the quicker the ship slows down
    private final float ACCELERATION_RATE = 35;

    private float currentRotationSpeed;
    private float currentSpeedX;
    private float currentSpeedY;
    private int energy;
    private Beam beam;
    private Storage storage;


    public Ship(){
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));

        currentRotationSpeed = 0;
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
        rotateLeft(tpf);
        haltIfToSlow(tpf);
    }

    public void accelerateForwards(float tpf) {
        currentSpeedY += tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400;
        adjustYSpeedIfNecessary();
    }
    
    public void accelerateBackwards(float tpf) {
        currentSpeedY -= tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400;
        adjustYSpeedIfNecessary();
    }
    
    public void accelerateLeft(float tpf) {
        currentSpeedX += tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400;
        adjustXSpeedIfNecessary();
    }
    
    public void accelerateRight(float tpf) {
        currentSpeedX -= tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400;
        adjustXSpeedIfNecessary();
    }
    
    public void accelerateTurnLeft(float tpf) {
        currentRotationSpeed += tpf * (ROTATION_ACCELERATION_RATE + ROTATION_DECELERATION_RATE) * 1.0/50;
        adjustRotationSpeedIfNecessary();
    }
    
    public void accelerateTurnRight(float tpf) {
        currentRotationSpeed -= tpf * (ROTATION_ACCELERATION_RATE + ROTATION_DECELERATION_RATE) * 1.0/50;
        adjustRotationSpeedIfNecessary();
    }

    //Helper
    private void adjustYSpeedIfNecessary() {
        if (currentSpeedY > MAX_SPEED/1000) {
            currentSpeedY = MAX_SPEED/1000;
        } else if (currentSpeedY < -MAX_SPEED/1000) {
            currentSpeedY = -MAX_SPEED/1000;
        }
    }

    //Helper
    private void adjustXSpeedIfNecessary() {
        if (currentSpeedX > MAX_SPEED/1000) {
            currentSpeedX = MAX_SPEED/1000;
        } else if (currentSpeedX < -MAX_SPEED/1000) {
            currentSpeedX = -MAX_SPEED/1000;
        }
    }
    
    private void adjustRotationSpeedIfNecessary() {
        if (currentRotationSpeed > MAX_ROTATION_SPEED/20) {
            currentRotationSpeed = MAX_ROTATION_SPEED/20;
        } else if (currentRotationSpeed < -MAX_ROTATION_SPEED/20) {
            currentRotationSpeed = -MAX_ROTATION_SPEED/20;
        }
    }

    private void haltIfToSlow(float tpf) {
        if (Math.abs(currentSpeedY) < 0.001) {
            currentSpeedY = 0;
        }
        if (Math.abs(currentSpeedX) < 0.001) {
            currentSpeedX = 0;
        }
        System.out.println(currentRotationSpeed);
        if (Math.abs(currentRotationSpeed) < tpf * ROTATION_DECELERATION_RATE * 1.0/50) {
            currentRotationSpeed = 0;
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
        body.rotateModel(currentRotationSpeed * tpf);
        if (currentRotationSpeed > 0) {
            currentRotationSpeed -= tpf * ROTATION_DECELERATION_RATE * 1.0/50;
        } else if (currentRotationSpeed < 0) {
            currentRotationSpeed += tpf * ROTATION_DECELERATION_RATE * 1.0/50;
        }
    }

    public void toggleBeam(boolean beamActive) {
        beam.setActive(beamActive);
    }

    public Beam getBeam(){return beam; }

    public Storage getStorage(){return storage; }

    public String getID() { return "ship"; }

    public float getCurrentSpeedX() {
        return currentSpeedX;
    }

    public float getCurrentSpeedY() {
        return currentSpeedY;
    }
}
