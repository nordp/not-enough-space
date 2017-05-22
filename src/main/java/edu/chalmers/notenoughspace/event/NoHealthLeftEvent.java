package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.Health;

/**
 * Created by Sparven on 2017-05-22.
 */
public class NoHealthLeftEvent {

    private Health health;

    public NoHealthLeftEvent(Health health) {
        this.health = health;
    }

    public Health getHealth() {
        return health;
    }
}
