package edu.chalmers.notenoughspace.core.entity.enemy;

import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;

/**
 *  Angry farmer who runs around the planet looking for the ship. If the ship is
 *  within a certain range, the farmer throws hayforks at it.
 */
public class Farmer extends Entity {

    private final static float AGGRO_DISTANCE = 10f;
    private final static float SPRINT_SPEED = 0.8f;
    private final static float TURN_RADIUS = 4;
    private final static float THROW_CHANCE = 0.7f;


    public Farmer(){
        super(new ZeroGravityStrategy());

        Bus.getInstance().post(new EntityCreatedEvent(this));
    }


    public void update(PlanetaryInhabitant ship, float tpf) {
        if (body.distanceTo(ship) <= AGGRO_DISTANCE) {
            chaseShip(ship, tpf);
            if (Math.random() * 100 <= THROW_CHANCE) {
                throwHayfork();
            }
        } else {
            randomlyStrollAround(tpf);
        }
    }


    private void chaseShip(PlanetaryInhabitant ship, float tpf) {
        float turnDir;

        PlanetaryInhabitant left = body.clone();
        PlanetaryInhabitant right = body.clone();

        left.rotateModel((float) Math.toRadians(TURN_RADIUS * tpf));
        left.rotateForward(SPRINT_SPEED * tpf);

        right.rotateModel((float) Math.toRadians(-TURN_RADIUS * tpf));
        right.rotateForward(SPRINT_SPEED * tpf);

        if (left.distanceTo(ship) < right.distanceTo(ship)) {
            turnDir = TURN_RADIUS;
        } else {
            turnDir = -TURN_RADIUS;
        }

        float angleToTurn = (float) Math.toRadians(turnDir);
        body.rotateModel(angleToTurn);
        if (left.distanceTo(ship) - right.distanceTo(ship) < 0.01f) { //TODO: Explain what this does.
            body.rotateForward(SPRINT_SPEED * tpf);
        }
    }

    private void randomlyStrollAround(float tpf) {
        if (Math.random() * 100 <= 10) {
            float randomDirection = (float) (Math.random()*180 - 180/2);
            float angleToTurn = 10 * tpf * (float) Math.toRadians(randomDirection);

            body.rotateModel(angleToTurn);
        }

        body.rotateForward(SPRINT_SPEED/3 * tpf);
    }

    private void throwHayfork() {
        new Hayfork(this);
    }

}