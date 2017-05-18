package edu.chalmers.notenoughspace.ctrl;

import com.jme3.app.SimpleApplication;
import de.lessvoid.nifty.Nifty;
import edu.chalmers.notenoughspace.core.*;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import org.junit.Test;


import javax.vecmath.Vector3f;

import static org.junit.Assert.*;

/**
 * Created by juliaortheden on 2017-05-11.
 */

public class ShipTest {


    @Test
    public void shipMovementTest(){
                Ship ship = new Ship();
                assertNotNull(ship.getBeam());

                assertFalse(ship.getBeam().isActive());

                ship.toggleBeam(true);
                assertTrue(ship.getBeam().isActive());

                assertNotNull(ship.getStorage());
            }

        };

