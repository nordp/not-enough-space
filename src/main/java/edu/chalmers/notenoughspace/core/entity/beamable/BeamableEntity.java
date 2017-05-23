package edu.chalmers.notenoughspace.core.entity.beamable;

import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.move.RealisticGravityStrategy;
import edu.chalmers.notenoughspace.event.BeamEnteredEvent;
import edu.chalmers.notenoughspace.event.BeamExitedEvent;
import edu.chalmers.notenoughspace.event.Bus;

public abstract class BeamableEntity extends Entity {
    private BeamState beamState = BeamState.NOT_IN_BEAM;

    public BeamableEntity() {
        super(new RealisticGravityStrategy());
    }

    public BeamState isInBeam(){ return beamState; }
    public void enterBeam(){
        this.beamState = BeamState.IN_BEAM;
        Bus.getInstance().post(new BeamEnteredEvent(this));
    }

    public void exitBeam(){
        this.beamState = BeamState.NOT_IN_BEAM;
        Bus.getInstance().post(new BeamExitedEvent(this));
    }

    public abstract float getWeight();
    public abstract float getPoints();
}
