package edu.chalmers.notenoughspace.core.move;

/**
 * Movement strategy for movement without acceleration/deceleration, directly corresponding to input.
 */
public class NoAccelerationStrategy extends MovementStrategy {

    private final float ROTATION_SPEED = 40;
    private final float SPEED = 40;
    private PlanetaryInhabitant body;
    
    public NoAccelerationStrategy(PlanetaryInhabitant body) {
        this.body = body;
        setCurrentYSpeed(SPEED/1000);
        setCurrentXSpeed(SPEED/1000);
        setCurrentRotationSpeed(ROTATION_SPEED/20);
    }


    public void move(PlanetaryInhabitant body, float tpf) {
        //Nothing here. All dependent on input.
    }

    public void addMoveInput(Movement movement, float tpf) {
        switch (movement) {
            case FORWARD:
                body.rotateForward(-1 * getCurrentYSpeed() * tpf * 20);
                break;
            case BACKWARD:
                body.rotateForward(1 * getCurrentYSpeed() * tpf * 20);
                break;
            case LEFT:
                body.rotateSideways(1 * getCurrentXSpeed() * tpf * 20);
                break;
            case RIGHT:
                body.rotateSideways(-1 * getCurrentXSpeed() * tpf * 20);
                break;
            case ROTATION_LEFT:
                body.rotateModel(getCurrentRotationSpeed() * tpf);
                break;
            case ROTATION_RIGHT:
                body.rotateModel(-getCurrentRotationSpeed() * tpf);
                break;
            default:
                System.out.println("Invalid movement as input.");
        }
    }

}
