package edu.chalmers.notenoughspace.core.entity.ship;

import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.HealthChangedEvent;
import edu.chalmers.notenoughspace.event.HealthEmptyEvent;

/**
 * The fitness of the ship, which drops when the ship is hit by hayforks and
 * satellites and rises when a health powerup is picked up.
 */
public class Health {

    private final int MAX_HEALTH;
    private int currentHealthLevel;
    private final float regenerationRate;

    public Health(int initialHealth, int MAX_HEALTH, float regenerationRate) {
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
            Bus.getInstance().post(new HealthEmptyEvent());
        } else if (currentHealthLevel > MAX_HEALTH) {
            currentHealthLevel = MAX_HEALTH;
        }

        if (currentHealthLevel != oldHealth) {
            Bus.getInstance().post(new HealthChangedEvent(currentHealthLevel));
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
