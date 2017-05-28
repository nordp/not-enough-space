package edu.chalmers.notenoughspace.event;

/**
 * Event fired when beam is turned on or off.
 */
public class BeamToggleEvent {

    private final boolean enabled;

    public BeamToggleEvent(boolean enabled){
        this.enabled = enabled;
        System.out.println("BeamToggleEvent fired.");
    }


    public boolean getEnabled(){
        return enabled;
    }

}
