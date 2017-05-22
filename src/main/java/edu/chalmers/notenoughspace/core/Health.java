package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.NoHealthLeftEvent;

/**
 * Created by Sparven on 2017-05-21.
 */
public class Health {

    private int healthLevel;

    public Health(int initialHealth) {
        this.healthLevel = initialHealth;
    }

    public void increaseHealth(int dHealth) {
        healthLevel += dHealth;
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
