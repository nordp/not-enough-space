package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * Created by Vibergf on 25/04/2017.
 */
public class Ship implements Entity {

    /**
     * The distance from the ship to the planet's surface.
     */
    public static final float ALTITUDE = 1.8f;
    public static final float SPEED = 1;
    public static final float ROTATION_SPEED = 2;

    private int energy;
    public Beam beam;

    public Ship(){
        Bus.getInstance().post(new EntityCreatedEvent(this));
//        super(parent);
        energy = 100;
        beam = new Beam(this);
//        detachChild(beam);

        //beamNode.setLocalTranslation(beamNode.getLocalTranslation().add(this.getChild("ship").getLocalTranslation()));
    }


    public void update() {

    }

    public boolean isBeamActive() {
        return beam.isActive();
    }

    public void setBeamActive(boolean beamActive) {
        beam.setActive(beamActive);
    }

}
