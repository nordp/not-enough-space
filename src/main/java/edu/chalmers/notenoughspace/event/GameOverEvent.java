package edu.chalmers.notenoughspace.event;

/**
 * Event fired when a game round is over.
 */
public class GameOverEvent {

    private final int score;

    public GameOverEvent(int score) {
        this.score = score;
        System.out.println("Game Over Event fired.");
    }


    public int getScore() {
       return score;
    }

}
