package edu.chalmers.notenoughspace.core.entity.enemy;

import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.ctrl.TestInhabitant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vibergf on 27/05/2017.
 */
public class FarmerTest {
    Ship ship;
    Farmer farmer;

    @Before
    public void setUp() throws Exception {
        ship = new Ship();
        farmer = new Farmer();
    }

    @Test
    public void farmerTest() throws Exception {
        ship.setPlanetaryInhabitant(new TestInhabitant(0f, 2f, 0f));
        farmer.setPlanetaryInhabitant(new TestInhabitant(5f, 2f, 0f));
        farmer.getPlanetaryInhabitant().setDirection(ship.getPlanetaryInhabitant().getPosition());
        float oldDistance = farmer.getPlanetaryInhabitant().distanceTo(ship.getPlanetaryInhabitant());
        farmer.update(ship.getPlanetaryInhabitant(), 0.02f);
        assertTrue(oldDistance > farmer.getPlanetaryInhabitant().distanceTo(ship.getPlanetaryInhabitant()));
    }
}
