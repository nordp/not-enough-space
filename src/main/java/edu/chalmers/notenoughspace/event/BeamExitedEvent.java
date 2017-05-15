package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.BeamableEntity;

/**
 * Created by Vibergf on 15/05/2017.
 */
public class BeamExitedEvent {

    public BeamableEntity beamable;

    public BeamExitedEvent(BeamableEntity beamable){
        this.beamable = beamable;
    }
}
