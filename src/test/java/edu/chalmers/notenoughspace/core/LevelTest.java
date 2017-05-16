package edu.chalmers.notenoughspace.core;

import com.jme3.math.FastMath;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Phnor on 2017-05-13.
 */
public class LevelTest {
    Level level;
    @Before
    public void setUp() throws Exception {
        level = new Level();
    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void getTimeLeft() throws Exception {
        float timeLeft = level.getTimeLeft();
        System.out.println(timeLeft);
        assertTrue(timeLeft == level.getTimeLeft());
        float elapsed = 0;
        float tick;
        while (0 < level.getTimeLeft()) {
            tick = FastMath.nextRandomFloat();
            elapsed += tick;
            level.update(tick);
            assertTrue(0.0001f > level.getTimeLeft() - (level.LEVEL_TIME - elapsed));
        }
    }

}