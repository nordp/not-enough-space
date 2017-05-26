package edu.chalmers.notenoughspace.core.move;

/**
 * Movement strategy for accelerating/decelerating movement.
 */
public class Accelerator extends MovementStrategy {

    private final float MAX_ROTATION_SPEED = 40;
    private final float ROTATION_ACCELERATION_RATE = 200;
    private final float ROTATION_DECELERATION_RATE = 200;

    private final float MAX_SPEED = 40;
    private final float DECELERATION_RATE = 45; //High deceleration rate = object slows down quickly.
    private final float ACCELERATION_RATE = 35; //High acceleration rate = object speeds up quickly.


    public void move(PlanetaryInhabitant body, float tpf) {
        moveForward(body, tpf);
        moveLeft(body, tpf);
        rotateLeft(body, tpf);

        haltIfToSlow(tpf);  //To prevent the object from oscillating around a balance point.
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
        double changeInSpeed = tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400;
        float newSpeed = getCurrentYSpeed() + (float) changeInSpeed;
        setCurrentYSpeed(newSpeed);

        adjustYSpeedIfNecessary();
    }

    private void accelerateBackward(float tpf) {
        double changeInSpeed = -tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400;
        float newSpeed = getCurrentYSpeed() + (float) changeInSpeed;
        setCurrentYSpeed(newSpeed);

        adjustYSpeedIfNecessary();
    }

    private void accelerateLeft(float tpf) {
        double changeInSpeed = tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400;
        float newSpeed = getCurrentXSpeed() + (float) changeInSpeed;
        setCurrentXSpeed(newSpeed);

        adjustXSpeedIfNecessary();
    }

    private void accelerateRight(float tpf) {
        double changeInSpeed = -tpf * (ACCELERATION_RATE + DECELERATION_RATE) * 1.0/400;
        float newSpeed = getCurrentXSpeed() + (float) changeInSpeed;
        setCurrentXSpeed(newSpeed);

        adjustXSpeedIfNecessary();
    }

    private void accelerateTurnLeft(float tpf) {
        double changeInSpeed = tpf * (ROTATION_ACCELERATION_RATE + ROTATION_DECELERATION_RATE) * 1.0/50;
        float newSpeed = getCurrentRotationSpeed() + (float) changeInSpeed;
        setCurrentRotationSpeed(newSpeed);

        adjustRotationSpeedIfNecessary();
    }

    private void accelerateTurnRight(float tpf) {
        double changeInSpeed = -tpf * (ROTATION_ACCELERATION_RATE + ROTATION_DECELERATION_RATE) * 1.0/50;
        float newSpeed = getCurrentRotationSpeed() + (float) changeInSpeed;
        setCurrentRotationSpeed(newSpeed);

        adjustRotationSpeedIfNecessary();
    }

    private void adjustYSpeedIfNecessary() {
        if (getCurrentYSpeed() > MAX_SPEED/1000) {
            setCurrentYSpeed(MAX_SPEED/1000);
        } else if (getCurrentYSpeed() < -MAX_SPEED/1000) {
            setCurrentYSpeed(-MAX_SPEED/1000);
        }
    }

    private void adjustXSpeedIfNecessary() {
        if (getCurrentXSpeed() > MAX_SPEED/1000) {
            setCurrentXSpeed(MAX_SPEED/1000);
        } else if (getCurrentXSpeed() < -MAX_SPEED/1000) {
            setCurrentXSpeed(-MAX_SPEED/1000);
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
        if (Math.abs(getCurrentYSpeed()) < 0.001) {
            setCurrentYSpeed(0);
        }
        if (Math.abs(getCurrentXSpeed()) < 0.001) {
            setCurrentXSpeed(0);
        }
        if (Math.abs(getCurrentRotationSpeed()) < tpf * ROTATION_DECELERATION_RATE * 1.0/50) {
            setCurrentRotationSpeed(0);
        }
    }

    private void moveForward(PlanetaryInhabitant body, float tpf) {
        float rotationAngle = -getCurrentYSpeed() * tpf * 20;
        body.rotateForward(rotationAngle);
        decelerateYSpeed(tpf);
    }

    private void moveLeft(PlanetaryInhabitant body, float tpf) {
        float rotationAngle = getCurrentXSpeed() * tpf * 20;
        body.rotateSideways(rotationAngle);
        decelerateXSpeed(tpf);
    }

    private void rotateLeft(PlanetaryInhabitant body, float tpf){
        body.rotateModel(getCurrentRotationSpeed() * tpf);
        decelerateRotationSpeed(tpf);
    }

    private void decelerateYSpeed(float tpf) {
        if (getCurrentYSpeed() > 0) {
            setCurrentYSpeed((float)(getCurrentYSpeed() - tpf * DECELERATION_RATE * 1.0/400));
        } else if (getCurrentYSpeed() < 0) {
            setCurrentYSpeed((float)(getCurrentYSpeed() + tpf * DECELERATION_RATE * 1.0/400));
        }
    }

    private void decelerateXSpeed(float tpf) {
        if (getCurrentXSpeed() > 0) {
            setCurrentXSpeed((float)(getCurrentXSpeed() - tpf * DECELERATION_RATE * 1.0/400));
        } else if (getCurrentXSpeed() < 0) {
            setCurrentXSpeed((float)(getCurrentXSpeed() + tpf * DECELERATION_RATE * 1.0/400));
        }
    }

    private void decelerateRotationSpeed(float tpf) {
        if (getCurrentRotationSpeed() > 0) {
            setCurrentRotationSpeed(
                    (float) (getCurrentRotationSpeed() - tpf * ROTATION_DECELERATION_RATE * 1.0 / 50));
        } else if (getCurrentRotationSpeed() < 0) {
            setCurrentRotationSpeed(
                    (float) (getCurrentRotationSpeed() + tpf * ROTATION_DECELERATION_RATE * 1.0 / 50));
        }
    }

}
