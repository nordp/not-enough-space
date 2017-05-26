package edu.chalmers.notenoughspace.core.entity.powerup;

/**
 * Factory for generating power-ups.
 */
public class PowerupFactory {

    private static float BONUS_CHANCE = 0.3f;

    public static Powerup createRandomPowerup(){
        float random = (float) Math.random();

        if (random <= BONUS_CHANCE) {
            return new HealthPowerup();
        } else {
            return new EnergyPowerup();
        }
    }
}
