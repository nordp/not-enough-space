package edu.chalmers.notenoughspace.core.spawn;

import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;

/**
 * Created by Phnor on 2017-05-23.
 */
public class EntitySpawner {
    private Spawn spawnStrategy;
    private final Ship ship;
    private Planet planet;

    public EntitySpawner(Spawn spawnStrategy, Ship ship, Planet planet) {
        this.ship = ship;
        this.spawnStrategy = spawnStrategy;
        this.planet = planet;
    }

    public void spawnUpdate(float tpf){

    }
}
