package edu.chalmers.notenoughspace.core.move;

import edu.chalmers.notenoughspace.ctrl.TestInhabitant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Vibergf on 27/05/2017.
 */
public class MovementTest {

    @Before
    public void setUp(){

    }

    @Test
    public void movementTest(){
        PlanetaryInhabitant body = new TestInhabitant(0f, 10f, 0f);
        MovementStrategy constantMovement = new ConstantMovementStrategy(40, 200);

        PlanetaryInhabitant clone = body.clone();
        constantMovement.addMoveInput(body, Movement.FORWARD, 0.02f);
        assertNotEquals(body.getPosition(), clone.getPosition());
        float distance1 = clone.distanceTo(body);

        clone = body.clone();
        constantMovement.addMoveInput(body, Movement.FORWARD, 0.02f);
        float distance2 = clone.distanceTo(body);

        assertEquals(distance1, distance2, 0.0001f);
    }
}
