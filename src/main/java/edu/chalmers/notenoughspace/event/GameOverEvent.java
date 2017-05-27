package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.Level;
import edu.chalmers.notenoughspace.core.entity.ship.Storage;

/**
 * Event fired when a game round is over.
 */
public class GameOverEvent {

    private int points;

    public GameOverEvent(int points) {
        this.points = points;
        System.out.println("Game Over Event fired.");
    }


    public float getPoints() {
       return points;
    }

}
