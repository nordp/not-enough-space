package edu.chalmers.notenoughspace.event;

/**
 * Created by Vibergf on 09/05/2017.
 */
public class BeamToggleEvent {
    private boolean enabled;

    public BeamToggleEvent(boolean enabled){
        this.enabled = enabled;
    }

    public boolean getEnabled(){
        return enabled;
    }
}
