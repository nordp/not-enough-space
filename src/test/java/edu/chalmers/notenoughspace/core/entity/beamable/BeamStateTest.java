package edu.chalmers.notenoughspace.core.entity.beamable;

import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.entity.ship.Beam;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.ctrl.TestInhabitant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeamStateTest {
    BeamableEntity beamable;
    Beam beam;
    Ship ship;

    @Before
    public void setUp() throws Exception {
        ship = new Ship();
        ship.setPlanetaryInhabitant(new TestInhabitant(0f, Planet.PLANET_RADIUS + Ship.ALTITUDE, 0f));
        beam = ship.getBeam();
        beam.setPlanetaryInhabitant(new TestInhabitant(0f, Planet.PLANET_RADIUS + Ship.ALTITUDE, 0f));
        beamable = new TestBeamable();
        beamable.setPlanetaryInhabitant(new TestInhabitant(0f, Planet.PLANET_RADIUS, 0f));
    }

    @Test
    public void beamStateTest() throws Exception {
        assertEquals(false, beamable.isInBeam());

        beamable.enterBeam();
        assertEquals(true, beamable.isInBeam());

        beamable.exitBeam();
        assertEquals(false, beamable.isInBeam());

        beamable.enterBeam();

        ship.toggleBeam(true);
        assertTrue(beam.isActive());
        assertEquals(100, ship.getEnergy(), 0f);
        ship.update(0.02f);
        assertNotEquals(0f, ship.getEnergy(),  0f);
        assertTrue(beam.isActive());
        for(int i = 0 ; i < 100 ; i++){
            ship.update(0.02f);
        }
        assertEquals(false, beamable.isInBeam());
        assertEquals(0, beam.getNumberOfObjectsInBeam());
        assertNotEquals(0, ship.getStorage().getTotalWeight());
    }

}