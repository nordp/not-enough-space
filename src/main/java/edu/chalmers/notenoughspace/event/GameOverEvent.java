package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.Level;
import edu.chalmers.notenoughspace.core.entity.ship.Storage;

/**
 * Created by Phnor on 2017-05-10.
 */
public class GameOverEvent {

    private int points;

    public GameOverEvent(int points) {
        this.points = points;
        System.out.println("Game Over Event");
    }

    public float getPoints(){
       return points;}
}
