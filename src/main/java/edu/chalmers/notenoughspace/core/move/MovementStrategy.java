package edu.chalmers.notenoughspace.core.move;

/**
 * Decides how an object reacts to movement input.
 */
public abstract class MovementStrategy {

    private float currentRotationSpeed;
    private float currentXSpeed;    //Regulating westward/eastward movement along the planet's surface.
    private float currentYSpeed;    //Regulating northward/southward movement along the planet's surface.

    public MovementStrategy() {
        currentRotationSpeed = 0;
        currentXSpeed = 0;
        currentYSpeed = 0;
    }


    public abstract void move(PlanetaryInhabitant body, float tpf);

    public abstract void addMoveInput(PlanetaryInhabitant body, Movement movement, float tpf);

    public float getCurrentRotationSpeed() {
        return currentRotationSpeed;
    }

    public float getCurrentXSpeed() {
        return currentXSpeed;
    }

    public float getCurrentYSpeed() {
        return currentYSpeed;
    }

    public void setCurrentRotationSpeed(float currentRotationSpeed) {
        this.currentRotationSpeed = currentRotationSpeed;
    }

    public void setCurrentXSpeed(float currentXSpeed) {
        this.currentXSpeed = currentXSpeed;
    }

    public void setCurrentYSpeed(float currentYSpeed) {
        this.currentYSpeed = currentYSpeed;
    }
}
