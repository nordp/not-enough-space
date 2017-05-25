package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.Entity;

/**
 * Created by Sparven on 2017-05-21.
 */
public class HayforkCollisionEvent {
    private Entity hayFork;

    public HayforkCollisionEvent(Entity hayFork) {
        this.hayFork = hayFork;
        System.out.println("HayforkCollisionEvent fired");
    }

    public Entity getHayFork() {
        return hayFork;
    }
}
