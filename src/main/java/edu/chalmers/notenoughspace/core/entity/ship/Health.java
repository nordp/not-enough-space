package edu.chalmers.notenoughspace.core.entity.ship;

import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.HealthChangedEvent;
import edu.chalmers.notenoughspace.event.HealthEmptyEvent;

/**
 * Created by Sparven on 2017-05-21.
 */
public class Health {

    private int healthLevel;
    private int maxHealth;

    public Health(int initialHealth, int maxHealth) {
        this.maxHealth = maxHealth;
        modifyHealth(initialHealth);
    }

    public void modifyHealth(int dHealth) {
        int oldHealth = healthLevel;
        healthLevel += dHealth;
        if (healthLevel <= 0) {
            healthLevel = 0;
            Bus.getInstance().post(new HealthEmptyEvent());
        }else if(healthLevel > maxHealth){
            healthLevel = maxHealth;
        }

        if(healthLevel != oldHealth)
            Bus.getInstance().post(new HealthChangedEvent(healthLevel));
    }

    public int getHealthLevel() {
        return healthLevel;
    }

    @Override
    public String toString() {
        return "Health: " + healthLevel + " HP";
    }
}
