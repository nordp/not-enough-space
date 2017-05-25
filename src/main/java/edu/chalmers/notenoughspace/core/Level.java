package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.entity.beamable.Cow;
import edu.chalmers.notenoughspace.core.entity.beamable.Junk;
import edu.chalmers.notenoughspace.core.entity.enemy.Farmer;
import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;
import edu.chalmers.notenoughspace.core.entity.powerup.HealthPowerup;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.GameOverEvent;
import edu.chalmers.notenoughspace.event.HealthEmptyEvent;

public class Level {


    public final int LEVEL_TIME = 120; //seconds

    private final EntitySpawner spawner;

    private CountDownTimer timer; //The total time the round has been active, in seconds.

    private Ship ship;

    private Planet planet;

    public Level() {
        ship = new Ship();
        planet = new Planet();
        //Test population
        spawner = new EntitySpawner(planet);
        spawner.spawn(Junk.class, 10);
        spawner.spawn(Cow.class, 10);
        spawner.spawn(Satellite.class, 1);
        spawner.spawn(Farmer.class, 1);
        spawner.spawn(HealthPowerup.class, 5);

        planet.randomizePositions();

//        spawner.addSpawnTimer(Satellite.class, 1);
        //Init timer.
        timer = new CountDownTimer(LEVEL_TIME) {
            @Override
            public void onTimeOut() {
                levelOver();
            }
        };
        Bus.getInstance().register(this);
    }

    public void update(float tpf) {
        timer.tick(tpf);
        spawner.update(tpf);
    }

    /**
     * Always call when you want to remove the Level object. Object will not be functional after calling this method.
     */
    public void cleanup() {
        Bus.getInstance().unregister(spawner);

        ship.cleanup();
        planet.cleanup();
        Bus.getInstance().unregister(this);
    }

    private void levelOver() {
        Bus.getInstance().post(new GameOverEvent(ship.getStorage().getScore()));
    }

    /**
     * @return time left on level in seconds
     */
    public float getTimeLeft() {
        return timer.getTimeLeft();
    }

    public float getShipsEnergy() {
        return ship.getEnergy();
    }

    public void start() {
    }

    @Subscribe
    public void shipOutOfHealth(HealthEmptyEvent event) {
        levelOver();
    }

}
