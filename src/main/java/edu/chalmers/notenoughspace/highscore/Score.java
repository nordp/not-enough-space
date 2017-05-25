package edu.chalmers.notenoughspace.highscore;

/**
 * Created by juliaortheden on 2017-05-23.
 */

    import java.io.Serializable;

    public class Score  implements Serializable {
        private int score;
        private String name;

        public int getScore() {
            return score;
        }

        public String getName() {
            return name;
        }

        public Score(String name, int score) {
            this.score = score;
            this.name = name;
        }
    }

