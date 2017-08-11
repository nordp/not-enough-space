package edu.chalmers.notenoughspace.event;

public class ShootEvent {

    /**
     * Event fired when the ship fires a shot.
     */

    private final boolean enabled;

    public ShootEvent(boolean enabled){
        this.enabled = enabled;
        //System.out.println("ShootEvent fired.");
    }


    public boolean getEnabled(){
        return enabled;
    }
}
