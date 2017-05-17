package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.BeamableEntity;

/**
 * Created by Sparven on 2017-05-16.
 */
public class EntityStoredEvent {
    private BeamableEntity beamedObject;

    public EntityStoredEvent(BeamableEntity beamedObject) {
        this.beamedObject = beamedObject;
        System.out.println("EntityStoredEvent fired");

    }

    public BeamableEntity getBeamableEntity(){
        return beamedObject;
    }
}
