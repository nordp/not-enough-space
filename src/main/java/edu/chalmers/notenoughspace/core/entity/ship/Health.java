package edu.chalmers.notenoughspace.core.entity.ship;

import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.HealthChangedEvent;
import edu.chalmers.notenoughspace.event.HealthEmptyEvent;

/**
 * The fitness of the ship, which drops when the ship is hit by hayforks and
 * satellites and rises when a health powerup is picked up.
 */
class Health {

    private final int MAX_HEALTH;
    private int currentHealthLevel;

    public Health(int initialHealth, int MAX_HEALTH) {
        this.MAX_HEALTH = MAX_HEALTH;
        modifyHealth(initialHealth);
    }


    public void modifyHealth(int dHealth) {
        int oldHealth = currentHealthLevel;
        currentHealthLevel += dHealth;

        if (currentHealthLevel <= 0) {
            currentHealthLevel = 0;
            Bus.getInstance().post(new HealthEmptyEvent());
        } else if (currentHealthLevel > MAX_HEALTH) {
            currentHealthLevel = MAX_HEALTH;
        }

        if(currentHealthLevel != oldHealth) {
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
