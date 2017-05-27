package edu.chalmers.notenoughspace.core.entity.powerup;

import edu.chalmers.notenoughspace.core.entity.ship.Ship;

/**
 * Power-up restoring some of the ship's health when collected.
 */
public class HealthPowerup extends Powerup {

    private static final int HEALING_POWER = 25;

    public void affect(Ship ship) {
        ship.modifyHealth(HEALING_POWER);
    }

}
