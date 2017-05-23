package edu.chalmers.notenoughspace.core.entity.beamable;

import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;

/**
 * Created by Phnor on 2017-05-08.
 */
public class Junk extends BeamableEntity {

    public Junk() {
        Bus.getInstance().post(new EntityCreatedEvent(this));
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
