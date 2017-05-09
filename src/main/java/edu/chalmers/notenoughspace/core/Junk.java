package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;

/**
 * Created by Phnor on 2017-05-08.
 */
public class Junk implements Entity{
    public Junk() {
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }
}
