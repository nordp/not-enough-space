package edu.chalmers.notenoughspace.core.entity.powerup;

/**
 * Created by Vibergf on 25/05/2017.
 */
public class PowerupFactory {

    public static Powerup createRandomPowerup(){
        float random = (float) Math.random();

        if(random < 0.3f){
            return new HealthPowerup();
        }else
            return new EnergyPowerup();
    }
}
