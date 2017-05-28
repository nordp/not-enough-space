package edu.chalmers.notenoughspace.highscore;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by juliaortheden on 2017-05-23.
 */
public class HighScoreManagerTest {

    @Test
    public void highScoreTest() {
        HighScoreManager hm = HighScoreManager.getHighScoreManager();
        assertNotNull(hm);

        ArrayList<Score> originalScores = hm.getScores(); //The getScores() method clones the list, so it's not needed here.
        assertNotNull(originalScores);

        hm.clearScores();
        assertTrue(hm.getScores().isEmpty());

        hm.addScore("Aa", 1);
        hm.addScore("Cc", 3);
        hm.addScore("Bb", 2);
        assertEquals(3, hm.getScores().size());
        assertEquals("Cc", hm.getScores().get(0).getName());

        hm.setScores(originalScores);
    }
}


