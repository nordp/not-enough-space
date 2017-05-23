package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.Entity;

/**
 * Created by Sparven on 2017-05-21.
 */
public class HayforkHitEvent {
    private Entity hayFork;

    public HayforkHitEvent(Entity hayFork) {
        this.hayFork = hayFork;
        System.out.println("HayforkHitEvent fired");
    }

    public Entity getHayFork() {
        return hayFork;
    }
}
