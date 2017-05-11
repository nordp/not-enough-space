package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.BeamableEntity;

import java.util.List;

/**
 * Created by Vibergf on 09/05/2017.
 */
public class BeamCollisionEvent {
    public List<BeamableEntity> collidingObjects;

    public BeamCollisionEvent(List<BeamableEntity> collidingCows){
        this.collidingObjects = collidingCows;
    }
}
