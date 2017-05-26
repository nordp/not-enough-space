package edu.chalmers.notenoughspace.core.entity.powerup;

import edu.chalmers.notenoughspace.core.entity.ship.Ship;

/**
 * Power-up restoring some of the ship's energy when collected.
 */
public class EnergyPowerup extends Powerup {

    private static final float ENERGY = 25;

    public void affect(Ship ship) {
        ship.modifyEnergy(ENERGY);
    }
}
