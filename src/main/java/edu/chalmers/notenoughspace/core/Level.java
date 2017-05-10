package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.GameOverEvent;

public class Level {


    private final int LEVEL_TIME = 120; //seconds

    private CountDownTimer timer; //The total time the round has been active, in seconds.

    private Ship ship;

    private Planet planet;


    public Level() {
        ship = new Ship();
        planet = new Planet();
        //Test population
        planet.populate(10, 10, 1);
        //Init timer.
        timer = new CountDownTimer(LEVEL_TIME) {
            @Override
            public void onTimeOut() {
                levelOver();
            }
        };
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

    public void start() {
    }
}
