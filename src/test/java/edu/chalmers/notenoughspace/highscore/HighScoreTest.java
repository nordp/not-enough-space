package edu.chalmers.notenoughspace.highscore;

import highscore.HighScoreManager;

/**
 * Created by juliaortheden on 2017-05-23.
 */
public class HighScoreTest {

    public void HighScoreTest() {
        HighScoreManager hm = new HighScoreManager();

        hm.addScore("Marge", 300);
        hm.addScore("Maggie", 220);
        hm.addScore("Homer", 100);
        hm.addScore("Lisa", 270);

        System.out.print(hm.getHighscoreString());
    }
}


