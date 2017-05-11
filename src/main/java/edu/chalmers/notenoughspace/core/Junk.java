package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;

/**
 * Created by Phnor on 2017-05-08.
 */
public class Junk implements Entity{

    private PlanetaryInhabitant body;

    public Junk() {
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }

    public PlanetaryInhabitant getPlanetaryInhabitant() {
        return body;
    }

    public void setPlanetaryInhabitant(PlanetaryInhabitant body) {
        this.body = body;
    }
}
