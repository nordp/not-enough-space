package edu.chalmers.notenoughspace.core.entity.beamable;

import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;

/**
 * Non-moving object residing on the surface of the planet. Possible for the ship to beam and store
 * but does not give any points or affect anything else.
 */
public class Junk extends BeamableEntity {

    public Junk() {
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }


    protected void onPlanetaryInhabitantAttached(){
        Entity.randomizeDirection(body);
    }

    public float getWeight() {
        return 1f;
    }

    public float getPoints() {
        return 0f;
    }

    public void update() {
        gravitate();
    }

}
