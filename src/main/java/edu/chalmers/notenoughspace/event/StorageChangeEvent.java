package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.Storage;

/**
 * Created by Phnor on 2017-05-17.
 */
public class StorageChangeEvent {
    private Storage storage;

    public StorageChangeEvent(Storage storage){
        this.storage = storage;
    }

    public float getNewScore(){
        return storage.calculateScore();
    }

    public float getNewWeight() {
        return storage.calculateWeight();
    }
}
