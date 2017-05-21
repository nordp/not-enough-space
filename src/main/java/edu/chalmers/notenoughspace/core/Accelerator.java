package edu.chalmers.notenoughspace.core;

/**
 * Created by Sparven on 2017-05-21.
 */
public class Accelerator extends MovementStrategy {

    private final float MAX_ROTATION_SPEED = 40;
    private final float ROTATION_ACCELERATION_RATE = 200;
    private final float ROTATION_DECELERATION_RATE = 200;

    private final float MAX_SPEED = 40;
    private final float DECELERATION_RATE = 45; //The higher the quicker the ship slows down
    private final float ACCELERATION_RATE = 35;

    public Accelerator(PlanetaryInhabitant body) {
        super(body);
    }

    public void move(float tpf) {
        moveForwards(tpf);
        moveLeft(tpf);
        rotateLeft(tpf);
        haltIfToSlow(tpf);
    }

    public void addMoveInput(Movement movement, float tpf) {
        switch (movement) {
            case FORWARD:
                accelerateForward(tpf);
                break;
            case BACKWARD:
                accelerateBackward(tpf);
                break;
            case LEFT:
                accelerateLeft(tpf);
                break;
            case RIGHT:
                accelerateRight(tpf);
                break;
            case ROTATION_LEFT:
                accelerateTurnLeft(tpf);
                break;
            case ROTATION_RIGHT:
                accelerateTurnRight(tpf);
                break;
            default:
                System.out.println("Invalid movement as input.");
        }
    }

    private void accelerateForward(float tpf) {
        currentSpeedY += tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400;
        adjustYSpeedIfNecessary();
    }

    private void accelerateBackward(float tpf) {
        currentSpeedY -= tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400;
        adjustYSpeedIfNecessary();
    }

    private void accelerateLeft(float tpf) {
        currentSpeedX += tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400;
        adjustXSpeedIfNecessary();
    }

    private void accelerateRight(float tpf) {
        currentSpeedX -= tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400;
        adjustXSpeedIfNecessary();
    }

    private void accelerateTurnLeft(float tpf) {
        currentRotationSpeed += tpf * (ROTATION_ACCELERATION_RATE + ROTATION_DECELERATION_RATE) * 1.0/50;
        adjustRotationSpeedIfNecessary();
    }

    private void accelerateTurnRight(float tpf) {
        currentRotationSpeed -= tpf * (ROTATION_ACCELERATION_RATE + ROTATION_DECELERATION_RATE) * 1.0/50;
        adjustRotationSpeedIfNecessary();
    }

    private void adjustYSpeedIfNecessary() {
        if (currentSpeedY > MAX_SPEED/1000) {
            currentSpeedY = MAX_SPEED/1000;
        } else if (currentSpeedY < -MAX_SPEED/1000) {
            currentSpeedY = -MAX_SPEED/1000;
        }
    }

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

    private void rotateLeft(float tpf){
        body.rotateModel(currentRotationSpeed * tpf);
        if (currentRotationSpeed > 0) {
            currentRotationSpeed -= tpf * ROTATION_DECELERATION_RATE * 1.0/50;
        } else if (currentRotationSpeed < 0) {
            currentRotationSpeed += tpf * ROTATION_DECELERATION_RATE * 1.0/50;
        }
    }

}
