package edu.chalmers.notenoughspace.highscore;

/**
 * Created by juliaortheden on 2017-05-23.
 */
public class HighScoreTest {

    public void HighScoreTest() {
        HighScoreManager hm = HighScoreManager.getHighScoreManager();

        hm.addScoreToList("Jonas", 300);
        hm.addScoreToList("Fredrik", 220);
        hm.addScoreToList("Philip", 100);
        hm.addScoreToList("Julia", 570);

        System.out.print(hm.getHighScoreString());
    }
}


