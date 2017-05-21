package edu.chalmers.notenoughspace.core;

/**
 * Created by Sparven on 2017-05-21.
 */
public class NoAccelerationStrategy extends MovementStrategy {

    private final float ROTATION_SPEED = 40;
    private final float SPEED = 40;
    
    public NoAccelerationStrategy(PlanetaryInhabitant body) {
        super(body);
        setCurrentSpeedY(SPEED/1000);
        setCurrentSpeedX(SPEED/1000);
        setCurrentRotationSpeed(ROTATION_SPEED/20);
    }

    public void move(float tpf) {
        //Nothing here. All dependent on input.
    }

    public void addMoveInput(Movement movement, float tpf) {
        switch (movement) {
            case FORWARD:
                getBody().rotateForward(-1 * getCurrentSpeedY() * tpf * 20);
                break;
            case BACKWARD:
                getBody().rotateForward(1 * getCurrentSpeedY() * tpf * 20);
                break;
            case LEFT:
                getBody().rotateSideways(1 * getCurrentSpeedX() * tpf * 20);
                break;
            case RIGHT:
                getBody().rotateSideways(-1 * getCurrentSpeedX() * tpf * 20);
                break;
            case ROTATION_LEFT:
                getBody().rotateModel(getCurrentRotationSpeed() * tpf);
                break;
            case ROTATION_RIGHT:
                getBody().rotateModel(-getCurrentRotationSpeed() * tpf);
                break;
            default:
                System.out.println("Invalid movement as input.");
        }
    }
}
