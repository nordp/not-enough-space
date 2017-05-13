package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;

/**
 * Created by Phnor on 2017-05-13.
 */
public class Farmer implements Entity{

    private final static float AGGRO_DISTANCE = 10f;
    private final static float SPRINT_SPEED = 0.8f;
    private final static float TURN_RADIUS = 5;

    private PlanetaryInhabitant body;

    public Farmer(){
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }

    public void update(PlanetaryInhabitant ship, float tpf) {
        if (body.distance(ship) < AGGRO_DISTANCE){
            PlanetaryInhabitant left = body.clone();
            PlanetaryInhabitant right = body.clone();

            left.rotateModel((float)Math.toRadians(TURN_RADIUS * tpf));
            left.rotateForward(SPRINT_SPEED * tpf);

            right.rotateModel((float)Math.toRadians(-TURN_RADIUS * tpf));
            right.rotateForward(SPRINT_SPEED * tpf);

            float turnDir;
            turnDir = (left.distance(ship) < right.distance(ship)) ? TURN_RADIUS : -TURN_RADIUS;
            body.rotateModel((float) Math.toRadians(turnDir));
            if (left.distance(ship) - right.distance(ship) < 0.01f) {
                body.rotateForward(SPRINT_SPEED * tpf);
            }
        }
    }

    public PlanetaryInhabitant getPlanetaryInhabitant() {
        return body;
    }

    public void setPlanetaryInhabitant(PlanetaryInhabitant body) {
        this.body = body;
    }
}