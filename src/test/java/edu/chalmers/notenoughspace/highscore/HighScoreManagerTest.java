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

        ArrayList<Score> scores = hm.getScores();
        ArrayList<Score> originalScores = (ArrayList<Score>) scores.clone();
        assertNotNull(scores);

        hm.clearScores();
        assertTrue(scores.isEmpty());

        hm.addScoreToList("Aa", 1);
        hm.addScoreToList("Cc", 3);
        hm.addScoreToList("Bb", 2);
        assertEquals(3, scores.size());
        assertEquals("Cc", scores.get(0).getName());

        hm.setScores(originalScores);
    }
}


