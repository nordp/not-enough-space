package edu.chalmers.notenoughspace.core.move;

/**
 * Movement strategy for movement without acceleration/deceleration, directly corresponding to input.
 */
public class ConstantMovementStrategy extends MovementStrategy {
    
    public ConstantMovementStrategy(float speed, float rotationSpeed) {
        setCurrentYSpeed(speed/1000);
        setCurrentXSpeed(speed/1000);
        setCurrentRotationSpeed(rotationSpeed/20);
    }


    public void move(PlanetaryInhabitant body, float tpf) {
        //Nothing here. All dependent on input.
    }

    public void addMoveInput(PlanetaryInhabitant body, Movement movement, float tpf) {
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
