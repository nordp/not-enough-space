package edu.chalmers.notenoughspace.event;

/**
 * Event fired when the ship's health level is changed.
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
