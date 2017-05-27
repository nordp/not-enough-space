package edu.chalmers.notenoughspace.core.entity;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.entity.beamable.BeamableEntity;
import edu.chalmers.notenoughspace.core.entity.beamable.Cow;
import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;
import edu.chalmers.notenoughspace.core.entity.powerup.HealthPowerup;
import edu.chalmers.notenoughspace.core.entity.powerup.Powerup;
import edu.chalmers.notenoughspace.event.BeamableStoredEvent;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityRemovedEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Phnor on 2017-05-13.
 */
public class PlanetTest {
    Planet planet;

    boolean entityRemovedEventFired = false;

    @Before
    public void setUp() throws Exception {
        planet = new Planet();
        Bus.getInstance().register(this);
    }

    @Test
    public void populationTest() throws Exception {
        assertEquals("planet", planet.getID());
        Satellite satellite = new Satellite();
        planet.addInhabitant(satellite);
        satellite.collision();

        assertTrue(entityRemovedEventFired);
        entityRemovedEventFired = false;

        Powerup powerup = new HealthPowerup();
        planet.addInhabitant(powerup);
        powerup.collision();

        assertTrue(entityRemovedEventFired);
        entityRemovedEventFired = false;

        BeamableEntity beamable = new Cow();
        planet.addInhabitant(beamable);
        Bus.getInstance().post(new BeamableStoredEvent(beamable));

        assertTrue(entityRemovedEventFired);

    }

    @Subscribe
    public void entityRemoved(EntityRemovedEvent event){
        entityRemovedEventFired = true;
    }

}