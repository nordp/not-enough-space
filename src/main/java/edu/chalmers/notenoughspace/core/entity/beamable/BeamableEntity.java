package edu.chalmers.notenoughspace.core.entity.beamable;

import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.move.RealisticGravityStrategy;
import edu.chalmers.notenoughspace.event.BeamEnteredEvent;
import edu.chalmers.notenoughspace.event.BeamExitedEvent;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * Entity possible for the ship to beam up and store in storage.
 */
public abstract class BeamableEntity extends Entity {

    private boolean inBeam;

    public BeamableEntity() {
        super(new RealisticGravityStrategy());
        inBeam = false;
    }


    public boolean isInBeam(){ return inBeam; }

    public void enterBeam(){
        inBeam = true;
        Bus.getInstance().post(new BeamEnteredEvent(this));
    }

    public void exitBeam(){
        inBeam = false;
        Bus.getInstance().post(new BeamExitedEvent(this));
    }

    public abstract float getWeight();

    public abstract float getPoints();

}
