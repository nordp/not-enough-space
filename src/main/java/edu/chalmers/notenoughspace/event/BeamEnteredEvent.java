package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.beamable.BeamableEntity;

/**
 * Created by Vibergf on 15/05/2017.
 */
public class BeamEnteredEvent {

    private BeamableEntity beamable;

    public BeamEnteredEvent(BeamableEntity beamable){

        this.beamable = beamable;
        System.out.println("BeamEnteredEvent fired.");
    }

    public BeamableEntity getBeamable(){
        return beamable;
    }
}
