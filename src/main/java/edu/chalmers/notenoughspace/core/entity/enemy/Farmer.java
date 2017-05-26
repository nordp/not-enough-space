package edu.chalmers.notenoughspace.core.entity.enemy;

import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;

/**
 * Created by Phnor on 2017-05-13.
 */
public class Farmer extends Entity {

    private final static float AGGRO_DISTANCE = 10f;
    private final static float SPRINT_SPEED = 0.8f;
    private final static float TURN_RADIUS = 5;
    private final static float THROW_CHANCE = 0.2f;


    public Farmer(){
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }

    public void update(PlanetaryInhabitant ship, float tpf) {
        if (body.distanceTo(ship) < AGGRO_DISTANCE){
            PlanetaryInhabitant left = body.clone();
            PlanetaryInhabitant right = body.clone();

            left.rotateModel((float)Math.toRadians(TURN_RADIUS * tpf));
            left.rotateForward(SPRINT_SPEED * tpf);

            right.rotateModel((float)Math.toRadians(-TURN_RADIUS * tpf));
            right.rotateForward(SPRINT_SPEED * tpf);

            float turnDir;
            turnDir = (left.distanceTo(ship) < right.distanceTo(ship)) ? TURN_RADIUS : -TURN_RADIUS;
            body.rotateModel((float) Math.toRadians(turnDir));
            if (left.distanceTo(ship) - right.distanceTo(ship) < 0.01f) {
                body.rotateForward(SPRINT_SPEED * tpf);
            }

            if (Math.random() * 100 < THROW_CHANCE) {
                throwHayfork();
            }
        }
    }

    private void throwHayfork() {
        new Hayfork(this);
    }

}