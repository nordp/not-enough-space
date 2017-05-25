package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.ship.Storage;

/**
 * Created by Phnor on 2017-05-17.
 */
public class StorageChangeEvent {
    private Storage storage;

    public StorageChangeEvent(Storage storage){
        this.storage = storage;
    }

    public int getNewScore(){
        return storage.getScore();
    }

    public int getNumberOfCows() {
        return storage.getNumberOfCows();
    }

    public float getNewWeight() {
        return storage.calculateWeight();
    }
}
