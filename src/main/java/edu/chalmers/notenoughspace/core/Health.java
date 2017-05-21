package edu.chalmers.notenoughspace.core;

/**
 * Created by Sparven on 2017-05-21.
 */
public class Health {

    private int healthLevel;

    public Health(int initialHealth) {
        this.healthLevel = healthLevel;
    }

    public void increaseHealth(int dHealth) {
        healthLevel += dHealth;
        if (healthLevel <= 0) {
            //TODO: Throw new GameOverEvent, but how to reach Level object?
        }
    }

    public int getHealthLevel() {
        return healthLevel;
    }
}
