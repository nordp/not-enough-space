package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.core.spawn.EntitySpawner;
import edu.chalmers.notenoughspace.core.spawn.Spawn;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.GameOverEvent;
import edu.chalmers.notenoughspace.event.NoHealthLeftEvent;

public class Level {


    public final int LEVEL_TIME = 120; //seconds

    private CountDownTimer timer; //The total time the round has been active, in seconds.

    private Ship ship;

    private Planet planet;


    public Level() {
        ship = new Ship();
        planet = new Planet();
        //Test population
        planet.populate(10, 10, 1, 1);
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
    }

    private void levelOver() {
        Bus.getInstance().post(new GameOverEvent(this));

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
    public void shipOutOfHealth(NoHealthLeftEvent event) {
        levelOver();
    }
}
