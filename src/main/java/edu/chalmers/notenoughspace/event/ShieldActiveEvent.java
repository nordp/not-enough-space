package edu.chalmers.notenoughspace.event;

/**
 * Event fired when the ship activates the shield.
 */

public class ShieldActiveEvent {

    private final boolean enabled;

    public ShieldActiveEvent(boolean enabled){
        this.enabled = enabled;
        System.out.println("ShieldActiveEvent fired.");
    }


    public boolean getEnabled(){
        return enabled;
    }
}
