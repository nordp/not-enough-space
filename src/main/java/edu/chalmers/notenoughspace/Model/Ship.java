package edu.chalmers.notenoughspace.Model;

/**
 * Created by Vibergf on 25/04/2017.
 */
public class Ship {

    public static final float SPEED = 1;
    public static final float ROTATION_SPEED = 2;

    private int energy;
    private boolean beamActive;

    public Ship(){
        energy = 100;
        beamActive = false;
    }

    public boolean isBeamActive() {
        return beamActive;
    }

    public void setBeamActive(boolean beamActive) {
        this.beamActive = beamActive;
    }
}
