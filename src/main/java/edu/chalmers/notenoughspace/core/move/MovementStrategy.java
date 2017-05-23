package edu.chalmers.notenoughspace.core.move;

/**
 * Created by Sparven on 2017-05-21.
 */
public abstract class MovementStrategy {

    private PlanetaryInhabitant body;

    private float currentRotationSpeed;
    private float currentSpeedX;
    private float currentSpeedY;

    public MovementStrategy(PlanetaryInhabitant body) {
        this.body = body;
        currentRotationSpeed = 0;
        currentSpeedX = 0;
        currentSpeedY = 0;
    }

    public abstract void move(float tpf);

    public abstract void addMoveInput(Movement movement, float tpf);

    protected PlanetaryInhabitant getBody() {
        return body;
    }

    public float getCurrentRotationSpeed() {
        return currentRotationSpeed;
    }

    public float getCurrentSpeedX() {
        return currentSpeedX;
    }

    public float getCurrentSpeedY() {
        return currentSpeedY;
    }

    public void setCurrentRotationSpeed(float currentRotationSpeed) {
        this.currentRotationSpeed = currentRotationSpeed;
    }

    public void setCurrentSpeedX(float currentSpeedX) {
        this.currentSpeedX = currentSpeedX;
    }

    public void setCurrentSpeedY(float currentSpeedY) {
        this.currentSpeedY = currentSpeedY;
    }
}
