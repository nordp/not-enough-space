package edu.chalmers.notenoughspace.event;

/**
 * Created by Sparven on 2017-05-22.
 */
public class HealthChangedEvent {

    private int newHealth;

    public HealthChangedEvent(int newHealth) {
        this.newHealth = newHealth;
    }

    public int getNewHealth() {
        return newHealth;
    }
}
