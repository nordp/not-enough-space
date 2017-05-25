package edu.chalmers.notenoughspace.core.entity.powerup;

import edu.chalmers.notenoughspace.core.entity.ship.Ship;

/**
 * Created by Vibergf on 25/05/2017.
 */
public class EnergyPowerup extends Powerup {

    private static final float AMOUNT = 25;

    public void affect(Ship ship) {
        ship.modifyEnergy(AMOUNT);
    }
}
