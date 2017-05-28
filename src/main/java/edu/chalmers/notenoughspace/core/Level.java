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
import edu.chalmers.notenoughspace.highscore.HighScoreManager;

/**
 * Creates and updates the different parts of a level in the game.
 */
public class Level {

    public final int LEVEL_TIME_IN_SECONDS = 120;

    private final EntitySpawner spawner;
    private final CountDownTimer timer;
    private final Ship ship;
    private final Planet planet;

    public Level() {
        ship = new Ship();
        planet = new Planet();

        spawner = new EntitySpawner(planet);
        spawnObjects();
        spawnEnemies();
        planet.randomizeInhabitantPositions();

        timer = new CountDownTimer(LEVEL_TIME_IN_SECONDS) {
            @Override
            public void onTimeOut() {
                levelOver();
            }
        };

        HighScoreManager.getHighScoreManager(); //Make sure the HighScoreManager singleton is initialized.

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

    public float getTimeLeft() {
        return timer.getTimeLeft();
    }

    @Subscribe
    public void shipOutOfHealth(HealthEmptyEvent event) {
        levelOver();
    }


    private void levelOver() {
        int finalScore = ship.getScore();
        GameOverEvent gameOverEvent = new GameOverEvent(finalScore);
        Bus.getInstance().post(gameOverEvent);
    }

    private void spawnObjects() {
        spawner.spawn(Junk.class, 10);
        spawner.spawn(HealthPowerup.class, 5);
        spawner.spawn(Cow.class, 10);
    }

    private void spawnEnemies() {
        spawner.spawn(Satellite.class, 1);
        spawner.spawn(Farmer.class, 1);
    }

}
