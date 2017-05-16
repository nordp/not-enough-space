package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.BeamEnteredEvent;
import edu.chalmers.notenoughspace.event.BeamExitedEvent;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;

/**
 * Created by Phnor on 2017-05-08.
 */
public class Junk implements BeamableEntity{

    private PlanetaryInhabitant body;

    private BeamState beamState;

    public Junk() {
        beamState = BeamState.NOT_IN_BEAM;
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }

    public PlanetaryInhabitant getPlanetaryInhabitant() {
        return body;
    }

    public void setPlanetaryInhabitant(PlanetaryInhabitant body) {
        this.body = body;
    }

    public BeamState isInBeam() {
        return beamState;
    }

    public void enterBeam() {
        this.beamState = BeamState.IN_BEAM;
        Bus.getInstance().post(new BeamEnteredEvent(this));
    }

    public void exitBeam() {
        this.beamState = BeamState.NOT_IN_BEAM;
        Bus.getInstance().post(new BeamExitedEvent(this));
    }

    public float getWeight() {
        return 1f;
    }

    public float getPoints() {
        return 0f;
    }
}
