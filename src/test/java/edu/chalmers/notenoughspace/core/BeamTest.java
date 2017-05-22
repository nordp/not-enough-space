package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.ctrl.TestInhabitant;
import edu.chalmers.notenoughspace.event.BeamEnteredEvent;
import edu.chalmers.notenoughspace.event.BeamExitedEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeamTest {
    TestInhabitant body;
    Beam beam;

    @Before
    public void setUp() throws Exception {
        beam = new Beam(new Ship());
        body = new TestInhabitant(0,0,0);
    }

    @Test
    public void getPlanetaryInhabitant() throws Exception {
        assertNull(beam.getPlanetaryInhabitant());
        beam.setPlanetaryInhabitant(body);
        assertEquals(body,beam.getPlanetaryInhabitant());
        assertNotEquals(body.clone(), beam.getPlanetaryInhabitant());
    }

    @Test
    public void setPlanetaryInhabitant() throws Exception {
        beam.setPlanetaryInhabitant(null);
        assertNull(beam.getPlanetaryInhabitant());
        beam.setPlanetaryInhabitant(body);
        assertEquals(body, beam.getPlanetaryInhabitant());
        assertNotEquals(body.clone(), beam.getPlanetaryInhabitant());
    }

    @Test
    public void setActive() throws Exception {
        assertFalse(beam.isActive());
        beam.setActive(true);
        assertTrue(beam.isActive());
        beam.setActive(false);
        assertFalse(beam.isActive());
    }

    @Test
    public void addToBeam() throws Exception {
        assertTrue(beam.getNumberOfObjectsInBeam() == 0);

        BeamableEntity beamableEntity = new TestBeamable();

        beam.addToBeam(new BeamEnteredEvent(beamableEntity));
        assertTrue(beam.getNumberOfObjectsInBeam() == 1);

        beam.removeFromBeam(new BeamExitedEvent(beamableEntity));
        assertTrue(beam.getNumberOfObjectsInBeam() == 0);

        for (int i = 0; i < 10; i++) {
            beam.addToBeam(new BeamEnteredEvent(new TestBeamable()));
            assertTrue(beam.getNumberOfObjectsInBeam() == i + 1);
        }
    }

    @Test
    public void addTheSameItemTwice() throws Exception {
        assertTrue(beam.getNumberOfObjectsInBeam() == 0);

        BeamableEntity beamableEntity = new TestBeamable();

        beam.addToBeam(new BeamEnteredEvent(beamableEntity));
        assertTrue(beam.getNumberOfObjectsInBeam() == 1);

        beam.addToBeam(new BeamEnteredEvent(beamableEntity));
        assertTrue(beam.getNumberOfObjectsInBeam() == 1);

        beam.removeFromBeam(new BeamExitedEvent(new TestBeamable()));
        assertTrue(beam.getNumberOfObjectsInBeam() == 1);
    }
}