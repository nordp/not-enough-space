package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import com.sun.javaws.exceptions.InvalidArgumentException;
import edu.chalmers.notenoughspace.core.CountDownTimer;
import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.entity.beamable.Cow;
import edu.chalmers.notenoughspace.core.entity.beamable.Junk;
import edu.chalmers.notenoughspace.core.entity.enemy.Farmer;
import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityRemovedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phnor on 2017-05-23.
 */
public class EntitySpawner {

    private Planet planet;
    private List<SpawnTimer> timerList;
    private int frequency;

    public EntitySpawner(Planet planet){
        this(1, planet);
    }
    public EntitySpawner(int frequency, Planet planet) {
        this.frequency = frequency;
        this.planet = planet;
        timerList = new ArrayList<SpawnTimer>();
        Bus.getInstance().register(this);
    }

    /**
     * Updates the state by ticking all stored timers.
     * @param tpf
     * Time elapsed
     */
    public void update(float tpf){
        for (CountDownTimer timer : timerList) {
            timer.tick(tpf);
        }
    }

    /**
     * Called to replace entities removed.
     * Will cause an increase in population if frequency > 1;
     * @param event
     * The EntityRemovedEvent containing the entity removed.
     */
    @Subscribe
    public void entityRemoved(EntityRemovedEvent event){
        spawn(event.getEntity().getClass());
    }

    /**
     * Calls newInstance() on the entityClass and puts the entity in the planet population.
     * Number of spawned entities correspond to set frequency
     * @param entityClass
     * The implementation class of entity to spawn on the planet
     */
    public void spawn(Class<? extends Entity> entityClass) { spawn(entityClass, frequency); }

    /**
     * Calls newInstance() on the entityClass and puts the entity in the planet population.
     * @param entityClass
     * The implementation class of entity to spawn on the planet
     * @param n
     * The amount of entities to spawn
     */
    public void spawn(Class<? extends Entity> entityClass, int n){
        for (int i = 0; i < n; i++) {
            planet.populate(getNewInstanceUtil(entityClass));
        }
    }

    private Entity getNewInstanceUtil(Class<? extends Entity> entityClass) {
        Entity e;
        if (entityClass.equals(Cow.class)){
            return new Cow();
        } else if (entityClass.equals(Junk.class)) {
            return new Junk();
        } else if (entityClass.equals(Satellite.class)){
            return new Satellite();
        } else if (entityClass.equals(Farmer.class)){
            return new Farmer();
        } else {
            throw new IllegalArgumentException("Not a legal spawnable entity");
        }
    }

    /**
     * Adds a new spawn timer which will continously spawn entity of entityClass each spawnInterval seconds.
     * Amount of entities follow the frequency field
     * @param entityClass
     * The implementation class of entity to spawn on the planet
     * @param spawnInterval
     * The interval between spawned entities
     */
    public void addSpawnTimer(Class<? extends Entity> entityClass, int spawnInterval) {
        timerList.add(new SpawnTimer(entityClass, spawnInterval));
    }



    //Private timer class
    private class SpawnTimer extends CountDownTimer {
        private Class<? extends Entity> entityClass;
        private final float spawnInterval;

        public SpawnTimer(final Class<? extends Entity> entityClass, final float spawnInterval) {
            super(spawnInterval);
            this.entityClass = entityClass;
            this.spawnInterval = spawnInterval;
        }

        public void onTimeOut() {
            spawn(entityClass);
            timeLeft = spawnInterval;
            running = true;
        }
    }
}


