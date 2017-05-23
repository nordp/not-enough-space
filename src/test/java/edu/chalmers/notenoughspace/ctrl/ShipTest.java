package edu.chalmers.notenoughspace.ctrl;

import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import org.junit.Test;


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

        }

