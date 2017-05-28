package edu.chalmers.notenoughspace.highscore;

import java.io.Serializable;

/**
 * A game round score from the high score table.
 */
class Score implements Serializable {

    private final String name;
    private final int score;

    public Score(String name, int score) {
        this.score = score;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

}

