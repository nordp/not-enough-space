package edu.chalmers.notenoughspace.core.move;

import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.ctrl.TestInhabitant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vibergf on 27/05/2017.
 */
public class GravityTest {

    @Before
    public void setUp(){

    }

    @Test
    public void gravityTest(){
        PlanetaryInhabitant body = new TestInhabitant(0f, Planet.PLANET_RADIUS, 0f);
        GravityStrategy zeroGravity = new ZeroGravityStrategy();

        assertEquals(Planet.PLANET_RADIUS, body.getDistanceFromPlanetsCenter(), 0f);
        for(int i = 0 ; i < 200 ; i++)
            zeroGravity.gravitate(body, 0.02f);
        assertEquals(Planet.PLANET_RADIUS, body.getDistanceFromPlanetsCenter(), 0f);

        GravityStrategy realisticGravity = new RealisticGravityStrategy();
        body.setDistanceFromPlanetsCenter(Planet.PLANET_RADIUS + 3f);
        assertEquals(Planet.PLANET_RADIUS + 3f, body.getDistanceFromPlanetsCenter(), 0f);

        for(int i = 0 ; i < 200; i++){
            realisticGravity.gravitate(body, 0.02f);
        }
        assertEquals(Planet.PLANET_RADIUS, body.getDistanceFromPlanetsCenter(), 0f);
    }
}
