package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import com.jme3.math.FastMath;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.GameOverEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Phnor on 2017-05-13.
 */
public class LevelTest {
    Level level;

    boolean timeOver = false;
    boolean gameOverEventFired = false;

    @Before
    public void setUp() throws Exception {
        level = new Level();
        Bus.getInstance().register(this);
    }

    @Test
    public void levelTest() throws Exception {
        CountDownTimer testTimer = new CountDownTimer(level.LEVEL_TIME_IN_SECONDS) {
            @Override
            public void onTimeOut() {
                timeOver = true;
            }
        };
        assertEquals(level.getTimeLeft(), testTimer.getTimeLeft(), 0f);


        float tick;
        while (!timeOver) {
            tick = FastMath.nextRandomFloat();
            testTimer.tick(tick);
            level.update(tick);
            assertEquals(level.getTimeLeft(), testTimer.getTimeLeft(), 0.001f);
        }
        assertEquals(0f, level.getTimeLeft(), 0f);
        assertTrue(gameOverEventFired);
    }

    @Subscribe
    public void gameOver(GameOverEvent event){
        gameOverEventFired = true;
    }

}