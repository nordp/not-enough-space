package edu.chalmers.notenoughspace.core.entity.ship;

import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EnergyChangedEvent;
import edu.chalmers.notenoughspace.event.EnergyEmptyEvent;

/**
 * Created by Vibergf on 25/05/2017.
 */
public class Energy {

    private float energyLevel;
    private float maxEnergy;
    private float regenerationRate;

    public Energy(float initialEnergy, float maxEnergy, float regenerationRate) {
        this.maxEnergy = maxEnergy;
        modifyEnergy(initialEnergy);
        this.regenerationRate = regenerationRate;
    }

    public void regenerate(float tpf){
        modifyEnergy(regenerationRate * tpf);
    }

    public void modifyEnergy(float dEnergy) {
        float oldEnergy = energyLevel;
        energyLevel += dEnergy;
        if (energyLevel <= 0) {
            energyLevel = 0;
            Bus.getInstance().post(new EnergyEmptyEvent());
        }else if(energyLevel > maxEnergy){
            energyLevel = maxEnergy;
        }

        if(energyLevel != oldEnergy)
            Bus.getInstance().post(new EnergyChangedEvent(energyLevel));
    }

    public float getEnergyLevel() {
                return energyLevel;
            }

    @Override
    public String toString() {
            return "Energy: " + energyLevel + " units";
        }

}
