package edu.chalmers.notenoughspace.ctrl;

import com.jme3.app.SimpleApplication;
import edu.chalmers.notenoughspace.core.Beam;
import edu.chalmers.notenoughspace.core.Planet;
import edu.chalmers.notenoughspace.core.PlanetaryInhabitant;
import edu.chalmers.notenoughspace.core.Ship;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import org.junit.Test;


import javax.vecmath.Vector3f;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by juliaortheden on 2017-05-11.
 */

/*
public class ShipTest {

    @Test
    public void shipMovementTest(PlanetaryInhabitant body) throws Exception {
        SimpleApplication app = new SimpleApplication() {
            @Override
            public void simpleInitApp() {
                Ship ship = new Ship();


                ship.moveForwards(body, 2f);
                assertTrue(getRootNode().getChild("ship").getWorldTranslation().equals(new Vector3f(2f, 0, 0)));

                ship.moveBackwards(body, 2f);
                assertTrue(getRootNode().getChild("ship").equals(new Vector3f(-2f, 0, 0)));

                ship.moveLeft(body, 2f);
                assertTrue(getRootNode().getChild("ship").equals(new Vector3f(0, 0, 2f)));

                ship.moveRight(body, 2f);
                assertTrue(getRootNode().getChild("ship").equals(new Vector3f(0, 0, -2f)));

                ship.rotateLeft(body, 2f);
                assertTrue(getRootNode().getChild("ship").equals(new Vector3f(0, 2f, 0)));

                ship.rotateRight(body, 2f);
                assertTrue(getRootNode().getChild("ship").equals(new Vector3f(0, 2f, 0)));

                ship.toggleBeam(true);
                assertEquals(beamActive, true);
            }

        }
    }


 */