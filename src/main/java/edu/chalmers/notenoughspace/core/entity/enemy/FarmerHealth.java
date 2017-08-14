package edu.chalmers.notenoughspace.core.entity.enemy;

import edu.chalmers.notenoughspace.event.*;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.HealthChangedEvent;

/**
 * The fitness of the farmer, which drops when the farmer is hit by shots, which results in that he has to wait to be able to throw
 * and rises with time.
 */
public class FarmerHealth {

    private final int MAX_HEALTH;
    private int currentHealthLevel;
    private final float regenerationRate;

    public FarmerHealth(int initialHealth, int MAX_HEALTH, float regenerationRate) {
        this.MAX_HEALTH = MAX_HEALTH;
        modifyHealth(initialHealth);
        this.regenerationRate = regenerationRate;
    }

    public void regenerate(float tpf){
        modifyHealth(regenerationRate * tpf);
    }


    public void modifyHealth(float dHealth) {
        int oldHealth = currentHealthLevel;
        currentHealthLevel += dHealth;

        if (currentHealthLevel <= 0) {
            currentHealthLevel = 0;
            //Bus.getInstance().post(new FarmerHealthEmptyEvent());
        } else if (currentHealthLevel > MAX_HEALTH) {
            currentHealthLevel = MAX_HEALTH;
        }
    }

    public int getCurrentHealthLevel() {
        return currentHealthLevel;
    }

    @Override
    public String toString() {
        return "Health: " + currentHealthLevel + " HP";
    }

}

