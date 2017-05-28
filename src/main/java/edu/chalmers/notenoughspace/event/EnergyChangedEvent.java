package edu.chalmers.notenoughspace.event;

/**
 * Event fired when the ship's energy level is changed.
 */
public class EnergyChangedEvent {

    private final float energyLevel;

    public EnergyChangedEvent(float energyLevel){
        this.energyLevel = energyLevel;
        System.out.println("EnergyLevelChangedEvent fired.");
    }


    public float getEnergyLevel(){
        return energyLevel;
    }

}
