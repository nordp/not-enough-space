package edu.chalmers.notenoughspace.core.move;

/**
 * Movement strategy for accelerating/decelerating movement.
 */
public class AccelerationMovementStrategy extends MovementStrategy {

    private float maxRotationSpeed;
    private float rotationAccelerationRate;
    private float rotationDecelerationRate;

    private float maxSpeed;
    private float decelerationRate; //High deceleration rate = object slows down quickly.
    private float accelerationRate; //High acceleration rate = object speeds up quickly.

    public AccelerationMovementStrategy(float maxSpeed, float accelerationRate, float maxRotationSpeed, float rotationAccelerationRate){
        this.maxSpeed = maxSpeed;
        this.accelerationRate = accelerationRate;
        this.decelerationRate = accelerationRate;
        this.maxRotationSpeed = maxRotationSpeed;
        this.rotationAccelerationRate = rotationAccelerationRate;
        this.rotationDecelerationRate = rotationAccelerationRate;
    }

    public void move(PlanetaryInhabitant body, float tpf) {
        moveForward(body, tpf);
        moveLeft(body, tpf);
        rotateLeft(body, tpf);

        stopIfTooSlow(tpf);  //To prevent the object from oscillating around a balance point.
    }

    public void addMoveInput(PlanetaryInhabitant body, Movement movement, float tpf) {
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
        double changeInSpeed = tpf * (accelerationRate + decelerationRate) * 1.0/400;
        float newSpeed = getCurrentYSpeed() + (float) changeInSpeed;
        setCurrentYSpeed(newSpeed);

        adjustYSpeedIfNecessary();
    }

    private void accelerateBackward(float tpf) {
        double changeInSpeed = -tpf * (accelerationRate + decelerationRate) * 1.0/400;
        float newSpeed = getCurrentYSpeed() + (float) changeInSpeed;
        setCurrentYSpeed(newSpeed);

        adjustYSpeedIfNecessary();
    }

    private void accelerateLeft(float tpf) {
        double changeInSpeed = tpf * (accelerationRate + decelerationRate) * 1.0/400;
        float newSpeed = getCurrentXSpeed() + (float) changeInSpeed;
        setCurrentXSpeed(newSpeed);

        adjustXSpeedIfNecessary();
    }

    private void accelerateRight(float tpf) {
        double changeInSpeed = -tpf * (accelerationRate + decelerationRate) * 1.0/400;
        float newSpeed = getCurrentXSpeed() + (float) changeInSpeed;
        setCurrentXSpeed(newSpeed);

        adjustXSpeedIfNecessary();
    }

    private void accelerateTurnLeft(float tpf) {
        double changeInSpeed = tpf * (rotationAccelerationRate + rotationDecelerationRate) * 1.0/50;
        float newSpeed = getCurrentRotationSpeed() + (float) changeInSpeed;
        setCurrentRotationSpeed(newSpeed);

        adjustRotationSpeedIfNecessary();
    }

    private void accelerateTurnRight(float tpf) {
        double changeInSpeed = -tpf * (rotationAccelerationRate + rotationDecelerationRate) * 1.0/50;
        float newSpeed = getCurrentRotationSpeed() + (float) changeInSpeed;
        setCurrentRotationSpeed(newSpeed);

        adjustRotationSpeedIfNecessary();
    }

    private void adjustYSpeedIfNecessary() {
        if (getCurrentYSpeed() > maxSpeed /1000) {
            setCurrentYSpeed(maxSpeed /1000);
        } else if (getCurrentYSpeed() < -maxSpeed /1000) {
            setCurrentYSpeed(-maxSpeed /1000);
        }
    }

    private void adjustXSpeedIfNecessary() {
        if (getCurrentXSpeed() > maxSpeed /1000) {
            setCurrentXSpeed(maxSpeed /1000);
        } else if (getCurrentXSpeed() < -maxSpeed /1000) {
            setCurrentXSpeed(-maxSpeed /1000);
        }
    }

    private void adjustRotationSpeedIfNecessary() {
        if (getCurrentRotationSpeed() > maxRotationSpeed /20) {
            setCurrentRotationSpeed(maxRotationSpeed /20);
        } else if (getCurrentRotationSpeed() < -maxRotationSpeed /20) {
            setCurrentRotationSpeed(-maxRotationSpeed /20);
        }
    }

    private void stopIfTooSlow(float tpf) {
        if (Math.abs(getCurrentYSpeed()) < 0.001) {
            setCurrentYSpeed(0);
        }
        if (Math.abs(getCurrentXSpeed()) < 0.001) {
            setCurrentXSpeed(0);
        }
        if (Math.abs(getCurrentRotationSpeed()) < tpf * rotationDecelerationRate * 1.0/50) {
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
            setCurrentYSpeed((float)(getCurrentYSpeed() - tpf * decelerationRate * 1.0/400));
        } else if (getCurrentYSpeed() < 0) {
            setCurrentYSpeed((float)(getCurrentYSpeed() + tpf * decelerationRate * 1.0/400));
        }
    }

    private void decelerateXSpeed(float tpf) {
        if (getCurrentXSpeed() > 0) {
            setCurrentXSpeed((float)(getCurrentXSpeed() - tpf * decelerationRate * 1.0/400));
        } else if (getCurrentXSpeed() < 0) {
            setCurrentXSpeed((float)(getCurrentXSpeed() + tpf * decelerationRate * 1.0/400));
        }
    }

    private void decelerateRotationSpeed(float tpf) {
        if (getCurrentRotationSpeed() > 0) {
            setCurrentRotationSpeed(
                    (float) (getCurrentRotationSpeed() - tpf * rotationDecelerationRate * 1.0 / 50));
        } else if (getCurrentRotationSpeed() < 0) {
            setCurrentRotationSpeed(
                    (float) (getCurrentRotationSpeed() + tpf * rotationDecelerationRate * 1.0 / 50));
        }
    }

}
