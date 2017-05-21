package edu.chalmers.notenoughspace.core;

/**
 * Created by Sparven on 2017-05-21.
 */
public abstract class MovementStrategy {

    protected PlanetaryInhabitant body;

    protected float currentRotationSpeed;
    protected float currentSpeedX;
    protected float currentSpeedY;

    public MovementStrategy(PlanetaryInhabitant body) {
        this.body = body;
        currentRotationSpeed = 0;
        currentSpeedX = 0;
        currentSpeedY = 0;
    }

    public abstract void move(float tpf);

    public abstract void addMoveInput(Movement movement, float tpf);

    public float getCurrentRotationSpeed() {
        return currentRotationSpeed;
    }

    public float getCurrentSpeedX() {
        return currentSpeedX;
    }

    public float getCurrentSpeedY() {
        return currentSpeedY;
    }
}
