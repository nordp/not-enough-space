package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.beamable.BeamableEntity;

/**
 * Event fired when beamable entity is stored into storage.
 */
public class BeamableStoredEvent {

    private BeamableEntity beamedObject;

    public BeamableStoredEvent(BeamableEntity beamedObject) {
        this.beamedObject = beamedObject;
        System.out.println("BeamableStoredEvent fired");
    }


    public BeamableEntity getBeamable(){
        return beamedObject;
    }

}
