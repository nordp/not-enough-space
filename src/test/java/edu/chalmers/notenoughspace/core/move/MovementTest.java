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
    PlanetaryInhabitant body;

    @Before
    public void setUp(){
        body = new TestInhabitant(0f, 10f, 0f);
    }

    @Test
    public void constantMovementTest() {
        MovementStrategy constantMovement = new ConstantMovementStrategy(40, 200);

        for (Movement m : Movement.values()) {
            PlanetaryInhabitant clone = body.clone();
            constantMovement.addMoveInput(body, m, 0.02f);
            assertNotEquals(body.getPosition(), clone.getPosition());
            float distance1 = clone.distanceTo(body);

            clone = body.clone();
            constantMovement.addMoveInput(body, m, 0.02f);
            float distance2 = clone.distanceTo(body);

            assertEquals(distance1, distance2, 0.0001f);
        }
    }

    @Test
    public void accelerationMovementTest(){
        MovementStrategy accelerationMovement = new AccelerationMovementStrategy(40, 45, 40, 200);

        for(Movement m : Movement.values()) {
            PlanetaryInhabitant clone = body.clone();
            accelerationMovement.addMoveInput(body, m, 0.02f);
            assertEquals(body.getPosition(), clone.getPosition());
            assertNotEquals(0f, accelerationMovement.getCurrentYSpeed() +
                    accelerationMovement.getCurrentXSpeed() + accelerationMovement.getCurrentRotationSpeed());

            accelerationMovement.move(body, 0.02f);
            assertNotEquals(body.getPosition(), clone.getPosition());

            for(int i = 0 ; i < 100 ; i++)
                accelerationMovement.move(body, 0.02f);

            assertEquals(0f, accelerationMovement.getCurrentYSpeed() +
                    accelerationMovement.getCurrentXSpeed() + accelerationMovement.getCurrentRotationSpeed(), 0f);

        }
    }
}
