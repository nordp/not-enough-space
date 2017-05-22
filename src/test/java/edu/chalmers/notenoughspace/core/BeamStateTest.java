package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.ctrl.TestInhabitant;
import edu.chalmers.notenoughspace.event.BeamEnteredEvent;
import edu.chalmers.notenoughspace.event.BeamExitedEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeamStateTest {
    BeamableEntity beamable;
    TestInhabitant body;
    Beam beam;

    @Before
    public void setUp() throws Exception {
        beam = new Beam(new Ship());
        beamable = new TestBeamable();
    }

    @Test
    public void isInBeam() throws Exception {
        assertTrue(beamable.isInBeam() == BeamState.NOT_IN_BEAM);

        beamable.enterBeam();
        assertTrue(beamable.isInBeam() == BeamState.IN_BEAM);

        beamable.exitBeam();
        assertTrue(beamable.isInBeam() == BeamState.NOT_IN_BEAM);
    }

}