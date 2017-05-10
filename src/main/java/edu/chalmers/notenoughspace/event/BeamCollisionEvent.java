package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.Beamable;

import java.util.List;

/**
 * Created by Vibergf on 09/05/2017.
 */
public class BeamCollisionEvent {
    public List<Beamable> collidingObjects;

    public BeamCollisionEvent(List<Beamable> collidingCows){
        this.collidingObjects = collidingCows;
    }
}
