package edu.chalmers.notenoughspace.event;

/**
 * Created by Sparven on 2017-05-22.
 */
public class HealthChangedEvent {

    private int healthLevel;

    public HealthChangedEvent(int healthLevel) {
        this.healthLevel = healthLevel;
    }

    public int getHealthLevel() {
        return healthLevel;
    }
}
