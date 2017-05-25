package edu.chalmers.notenoughspace.event;

/**
 * Created by Vibergf on 25/05/2017.
 */
public class EnergyChangedEvent {

    private float energyLevel;

    public EnergyChangedEvent(float energyLevel){
        this.energyLevel = energyLevel;
    }

    public float getEnergyLevel(){
        return this.energyLevel;
    }
}
