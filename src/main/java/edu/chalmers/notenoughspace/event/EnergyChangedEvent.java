package edu.chalmers.notenoughspace.event;

/**
 * Event fired when the ship's energy level is changed.
 */
public class EnergyChangedEvent {

    private final float energyLevel;

    public EnergyChangedEvent(float energyLevel){
        this.energyLevel = energyLevel;
    }


    public float getEnergyLevel(){
        return energyLevel;
    }

}
