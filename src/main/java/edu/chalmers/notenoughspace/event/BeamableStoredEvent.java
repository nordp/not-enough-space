package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.beamable.BeamableEntity;

/**
 * Created by Sparven on 2017-05-16.
 */
public class BeamableStoredEvent {
    private BeamableEntity beamedObject;

    public BeamableStoredEvent(BeamableEntity beamedObject) {
        this.beamedObject = beamedObject;
        System.out.println("BeamableStoredEvent fired");

    }

    public BeamableEntity getBeamableEntity(){
        return beamedObject;
    }
}
