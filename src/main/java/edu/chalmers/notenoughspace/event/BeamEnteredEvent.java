package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.beamable.BeamableEntity;

/**
 * Event fired when beamable entity enters the beam.
 */
public class BeamEnteredEvent {

    private final BeamableEntity beamable;

    public BeamEnteredEvent(BeamableEntity beamable){
        this.beamable = beamable;
        System.out.println("BeamEnteredEvent fired.");
    }


    public BeamableEntity getBeamable(){
        return beamable;
    }

}
