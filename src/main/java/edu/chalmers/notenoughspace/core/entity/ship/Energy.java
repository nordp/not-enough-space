package edu.chalmers.notenoughspace.core.entity.ship;

import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EnergyChangedEvent;
import edu.chalmers.notenoughspace.event.EnergyEmptyEvent;

/**
 *  The resource used to power beam. Is consumed constantly when beam
 *  is activated and gradually recharged when beam is inactive. Energy can also be
 *  recharged quickly by picking up a powerup of the energy type.
 */
class Energy {

    private final float MAX_ENERGY;
    private float currentEnergyLevel;
    private final float regenerationRate;

    public Energy(float initialEnergy, float MAX_ENERGY, float regenerationRate) {
        this.MAX_ENERGY = MAX_ENERGY;
        modifyEnergy(initialEnergy);
        this.regenerationRate = regenerationRate;
    }


    public void regenerate(float tpf){
        modifyEnergy(regenerationRate * tpf);
    }

    public void modifyEnergy(float changeInEnergy) {
        float oldEnergy = currentEnergyLevel;
        currentEnergyLevel += changeInEnergy;

        if (currentEnergyLevel <= 0) {
            currentEnergyLevel = 0;
            Bus.getInstance().post(new EnergyEmptyEvent());
        } else if (currentEnergyLevel > MAX_ENERGY) {
            currentEnergyLevel = MAX_ENERGY;
        }

        if (currentEnergyLevel != oldEnergy) {
            Bus.getInstance().post(new EnergyChangedEvent(currentEnergyLevel));
        }
    }

    public float getCurrentEnergyLevel() {
                return currentEnergyLevel;
            }

    @Override
    public String toString() {
            return "Energy: " + currentEnergyLevel + " units";
        }

}
