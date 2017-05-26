package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.entity.beamable.Cow;
import edu.chalmers.notenoughspace.core.entity.beamable.Junk;
import edu.chalmers.notenoughspace.core.entity.enemy.Farmer;
import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;
import edu.chalmers.notenoughspace.core.entity.powerup.EnergyPowerup;
import edu.chalmers.notenoughspace.core.entity.powerup.HealthPowerup;
import edu.chalmers.notenoughspace.core.entity.powerup.PowerupFactory;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityRemovedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates and places (spawns) new entities belonging to the planet.
 */
public class EntitySpawner {

    private List<SpawnTimer> timerList; //For entities to be spawned at regular intervals.
    private Planet planet;

    public EntitySpawner(Planet planet) {
        this.planet = planet;
        timerList = new ArrayList<SpawnTimer>();

        Bus.getInstance().register(this);
    }


    public void update(float tpf){
        for (CountDownTimer timer : timerList) {
            timer.tick(tpf);
        }
    }

    public void spawn(Class<? extends Entity> entityClass, int n){
        for (int i = 0; i < n; i++) {
            Entity newEntity = createNewEntity(entityClass);
            planet.addInhabitant(newEntity);
        }
    }

    public void spawn(Class<? extends Entity> entityClass) { spawn(entityClass, 1); }

    public void addSpawnTimer(Class<? extends Entity> classOfObjectToSpawn, int spawnInterval) {
        SpawnTimer spawnTimer = new SpawnTimer(classOfObjectToSpawn, spawnInterval);
        timerList.add(spawnTimer);
    }

    @Subscribe
    public void entityRemoved(EntityRemovedEvent event){
        Class<? extends Entity> classOfObjectToSpawn = event.getEntity().getClass();
        spawn(classOfObjectToSpawn);    //Spawns new instance of the removed entity,
                                        //to keep the total number of entities constant.
    }


    private Entity createNewEntity(Class<? extends Entity> entityClass) {
        if (entityClass.equals(Cow.class)){
            return new Cow();
        } else if (entityClass.equals(Junk.class)) {
            return new Junk();
        } else if (entityClass.equals(Satellite.class)){
            return new Satellite();
        } else if (entityClass.equals(Farmer.class)){
            return new Farmer();
        } else if (entityClass.equals(HealthPowerup.class) || entityClass.equals(EnergyPowerup.class)){
            return PowerupFactory.createRandomPowerup();
        } else {
            throw new IllegalArgumentException("Not a legal spawnable entity.");
        }
    }

    /**
     * Timer for spawning entities at regular intervals.
     */
    private class SpawnTimer extends CountDownTimer {
        private Class<? extends Entity> classOfObjectToSpawn;
        private float spawnInterval;

        private SpawnTimer(final Class<? extends Entity> classOfObjectToSpawn, final float spawnInterval) {
            super(spawnInterval);
            this.classOfObjectToSpawn = classOfObjectToSpawn;
            this.spawnInterval = spawnInterval;
        }

        public void onTimeOut() {
            spawn(classOfObjectToSpawn);
            timeLeft = spawnInterval;
            running = true;
        }
    }

}


