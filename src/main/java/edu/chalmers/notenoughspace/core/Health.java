package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.HealthChangedEvent;
import edu.chalmers.notenoughspace.event.NoHealthLeftEvent;

/**
 * Created by Sparven on 2017-05-21.
 */
public class Health {

    private int healthLevel;

    public Health(int initialHealth) {
        this.healthLevel = initialHealth;
        Bus.getInstance().post(new HealthChangedEvent(healthLevel));
    }

    public void increaseHealth(int dHealth) {
        healthLevel += dHealth;
        Bus.getInstance().post(new HealthChangedEvent(healthLevel));

        if (healthLevel <= 0) {
            Bus.getInstance().post(new NoHealthLeftEvent(this));
        }
    }

    public int getHealthLevel() {
        return healthLevel;
    }

    @Override
    public String toString() {
        return "Health: " + healthLevel + " HP";
    }
}
