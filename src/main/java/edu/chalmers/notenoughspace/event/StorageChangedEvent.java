package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.ship.Storage;

/**
 * Event fired when an object is added to or removed from the ship's storage.
 */
public class StorageChangedEvent {

    private int score;
    private int numberOfCows;

    public StorageChangedEvent(int score, int numberOfCows) {
        this.score = score;
        this.numberOfCows = numberOfCows;
    }


    public int getNewScore(){
        return score;
    }

    public int getNumberOfCows() {
        return numberOfCows;
    }

}
