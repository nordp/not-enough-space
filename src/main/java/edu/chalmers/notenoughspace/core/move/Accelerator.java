package edu.chalmers.notenoughspace.core.move;

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

    public void move(PlanetaryInhabitant body, float tpf) {
        moveForwards(body, tpf);
        moveLeft(body, tpf);
        rotateLeft(body, tpf);
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
        setCurrentSpeedY(
                (float) (getCurrentSpeedY() + tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400));
        adjustYSpeedIfNecessary();
    }

    private void accelerateBackward(float tpf) {
        setCurrentSpeedY(
                (float) (getCurrentSpeedY() - tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400));
        adjustYSpeedIfNecessary();
    }

    private void accelerateLeft(float tpf) {
        setCurrentSpeedX(
                (float) (getCurrentSpeedX() + tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400));
        adjustXSpeedIfNecessary();
    }

    private void accelerateRight(float tpf) {
        setCurrentSpeedX(
                (float) (getCurrentSpeedX() - tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400));
        adjustXSpeedIfNecessary();
    }

    private void accelerateTurnLeft(float tpf) {
        setCurrentRotationSpeed((float) (getCurrentRotationSpeed()
                + tpf * (ROTATION_ACCELERATION_RATE + ROTATION_DECELERATION_RATE) * 1.0/50));
        adjustRotationSpeedIfNecessary();
    }

    private void accelerateTurnRight(float tpf) {
        setCurrentRotationSpeed((float) (getCurrentRotationSpeed()
                - tpf * (ROTATION_ACCELERATION_RATE + ROTATION_DECELERATION_RATE) * 1.0/50));
        adjustRotationSpeedIfNecessary();
    }

    private void adjustYSpeedIfNecessary() {
        if (getCurrentSpeedY() > MAX_SPEED/1000) {
            setCurrentSpeedY(MAX_SPEED/1000);
        } else if (getCurrentSpeedY() < -MAX_SPEED/1000) {
            setCurrentSpeedY(-MAX_SPEED/1000);
        }
    }

    private void adjustXSpeedIfNecessary() {
        if (getCurrentSpeedX() > MAX_SPEED/1000) {
            setCurrentSpeedX(MAX_SPEED/1000);
        } else if (getCurrentSpeedX() < -MAX_SPEED/1000) {
            setCurrentSpeedX(-MAX_SPEED/1000);
        }
    }

    private void adjustRotationSpeedIfNecessary() {
        if (getCurrentRotationSpeed() > MAX_ROTATION_SPEED/20) {
            setCurrentRotationSpeed(MAX_ROTATION_SPEED/20);
        } else if (getCurrentRotationSpeed() < -MAX_ROTATION_SPEED/20) {
            setCurrentRotationSpeed(-MAX_ROTATION_SPEED/20);
        }
    }

    private void haltIfToSlow(float tpf) {
        if (Math.abs(getCurrentSpeedY()) < 0.001) {
            setCurrentSpeedY(0);
        }
        if (Math.abs(getCurrentSpeedX()) < 0.001) {
            setCurrentSpeedX(0);
        }
        if (Math.abs(getCurrentRotationSpeed()) < tpf * ROTATION_DECELERATION_RATE * 1.0/50) {
            setCurrentRotationSpeed(0);
        }
    }

    private void moveForwards(PlanetaryInhabitant body, float tpf) {
        body.rotateForward(-1 * getCurrentSpeedY() * tpf * 20);

        if (getCurrentSpeedY() > 0) {
            setCurrentSpeedY((float)(getCurrentSpeedY() - tpf * DECELERATION_RATE * 1.0/400));
        } else if (getCurrentSpeedY() < 0) {
            setCurrentSpeedY((float)(getCurrentSpeedY() + tpf * DECELERATION_RATE * 1.0/400));
        }
    }

    private void moveLeft(PlanetaryInhabitant body, float tpf) {
        body.rotateSideways(1 * getCurrentSpeedX() * tpf * 20);

        if (getCurrentSpeedX() > 0) {
            setCurrentSpeedX((float)(getCurrentSpeedX() - tpf * DECELERATION_RATE * 1.0/400));
        } else if (getCurrentSpeedX() < 0) {
            setCurrentSpeedX((float)(getCurrentSpeedX() + tpf * DECELERATION_RATE * 1.0/400));
        }
    }

    private void rotateLeft(PlanetaryInhabitant body, float tpf){
        body.rotateModel(getCurrentRotationSpeed() * tpf);
        if (getCurrentRotationSpeed() > 0) {
            setCurrentRotationSpeed(
                    (float) (getCurrentRotationSpeed() - tpf * ROTATION_DECELERATION_RATE * 1.0 / 50));
        } else if (getCurrentRotationSpeed() < 0) {
            setCurrentRotationSpeed(
                    (float) (getCurrentRotationSpeed() + tpf * ROTATION_DECELERATION_RATE * 1.0 / 50));
        }
    }

}
