package edu.chalmers.notenoughspace.event;

/**
 * Event fired when an object is added to or removed from the ship's storage.
 */
public class StorageChangedEvent {

    private final int score;
    private final int numberOfCows;

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
