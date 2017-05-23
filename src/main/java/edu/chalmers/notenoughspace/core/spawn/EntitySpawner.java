package edu.chalmers.notenoughspace.core.spawn;

import edu.chalmers.notenoughspace.core.entity.ship.Ship;

/**
 * Created by Phnor on 2017-05-23.
 */
public class EntitySpawner {
    private final Ship ship;

    public EntitySpawner(Ship ship, Spawn each) {
        this.ship = ship;
    }
}
