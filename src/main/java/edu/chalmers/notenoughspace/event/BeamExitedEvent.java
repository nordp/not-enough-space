package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.beamable.BeamableEntity;

/**
 * Event fired when beamable entity exits the beam.
 */
public class BeamExitedEvent {

    private final BeamableEntity beamable;

    public BeamExitedEvent(BeamableEntity beamable){
        this.beamable = beamable;
        System.out.println("BeamExitedEvent fired.");
    }


    public BeamableEntity getBeamable(){
        return beamable;
    }

}
