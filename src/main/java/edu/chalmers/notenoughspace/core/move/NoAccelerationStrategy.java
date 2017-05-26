package edu.chalmers.notenoughspace.core.move;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.List;
import java.util.Queue;

/**
 * Created by Sparven on 2017-05-21.
 */
public class NoAccelerationStrategy extends MovementStrategy {

    private final float ROTATION_SPEED = 40;
    private final float SPEED = 40;
    private PlanetaryInhabitant body;
    
    public NoAccelerationStrategy(PlanetaryInhabitant body) {
        this.body = body;
        setCurrentSpeedY(SPEED/1000);
        setCurrentSpeedX(SPEED/1000);
        setCurrentRotationSpeed(ROTATION_SPEED/20);
    }

    public void move(PlanetaryInhabitant body, float tpf) {
        //Nothing here. All dependent on input.
    }

    public void addMoveInput(Movement movement, float tpf) {
        switch (movement) {
            case FORWARD:
                body.rotateForward(-1 * getCurrentSpeedY() * tpf * 20);
                break;
            case BACKWARD:
                body.rotateForward(1 * getCurrentSpeedY() * tpf * 20);
                break;
            case LEFT:
                body.rotateSideways(1 * getCurrentSpeedX() * tpf * 20);
                break;
            case RIGHT:
                body.rotateSideways(-1 * getCurrentSpeedX() * tpf * 20);
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
