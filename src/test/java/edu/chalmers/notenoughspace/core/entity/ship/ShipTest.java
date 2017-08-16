package edu.chalmers.notenoughspace.core.entity.ship;

import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.entity.enemy.Farmer;
import edu.chalmers.notenoughspace.core.entity.enemy.Hayfork;
import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.ctrl.TestInhabitant;
import edu.chalmers.notenoughspace.event.HayforkCollisionEvent;
import edu.chalmers.notenoughspace.event.SatelliteCollisionEvent;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by juliaortheden on 2017-05-11.
 */

public class ShipTest {
    Beam beam;
    Ship ship;
    ShootWeapon shootWeapon;
    Shield shield;

    @Before
    public void setUp() throws Exception {
        ship = new Ship();
        ship.setPlanetaryInhabitant(new TestInhabitant(0f, Planet.PLANET_RADIUS + Ship.ALTITUDE, 0f));
        beam = ship.getBeam();
        beam.setPlanetaryInhabitant(new TestInhabitant(0f, Planet.PLANET_RADIUS + Ship.ALTITUDE, 0f));
        shield = ship.getShield();
        shield.setPlanetaryInhabitant(new TestInhabitant(0f, Planet.PLANET_RADIUS + Ship.ALTITUDE, 0f));
        shootWeapon = ship.getWeapon();
        shootWeapon.setPlanetaryInhabitant(new TestInhabitant(0f, Planet.PLANET_RADIUS + Ship.ALTITUDE, 0f));

    }

    @Test
    public void shipEnergyTest() throws Exception{
        assertEquals(100, ship.getEnergy(), 0f);

        ship.toggleBeam(true);
        ship.update(Beam.getEnergyCost() / 2);
        assertEquals(50, ship.getEnergy(), 0.01f);
        assertTrue(beam.isActive());

        ship.update(Beam.getEnergyCost() / 2);
        assertEquals(0, ship.getEnergy(), 0.01f);
        assertFalse(beam.isActive());

        ship.update(100 / 5);
        assertEquals(100, ship.getEnergy(), 0f);

        ship.toggleBeam(true);
        ship.update(Beam.getEnergyCost() * 2);
        assertEquals(0, ship.getEnergy(), 0f);
        assertFalse(beam.isActive());
    }

    @Test
    public void shipHealthTest() throws Exception{
        assertEquals(100, ship.getHealth(), 0f);

        Hayfork hayfork = new Hayfork(new Farmer());
        ship.hayforkCollision(new HayforkCollisionEvent(hayfork.getID(), hayfork.getDamage()));
        assertNotEquals(100, ship.getHealth());

        ship.modifyHealth(1000);
        assertEquals(100, ship.getHealth(), 0f);
        ship.satelliteCollision(new SatelliteCollisionEvent(new Satellite()));
        assertNotEquals(100, ship.getHealth());

        ship.modifyHealth(-1000);
        assertEquals(0, ship.getHealth(), 0f);
    }

}

