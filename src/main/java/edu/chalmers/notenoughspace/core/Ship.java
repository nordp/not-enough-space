package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.BeamCollisionEvent;
import edu.chalmers.notenoughspace.event.Bus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vibergf on 25/04/2017.
 */
public class Ship implements Entity {

    public static final float SPEED = 1;
    public static final float ROTATION_SPEED = 2;

    private int energy;
    public Beam beam;

    public Ship(){
//        super(parent);
        Bus.getInstance().post(new EntityCreatedEvent(this));
        energy = 100;
        beam = new Beam(this);
//        detachChild(beam);


        //beamNode.setLocalTranslation(beamNode.getLocalTranslation().add(this.getChild("ship").getLocalTranslation()));
    }


    public void update() {

    }

    public boolean isBeamActive() {
        return beam.isBeamActive();
    }

    public void setBeamActive(boolean beamActive) {
        beam.setActive(beamActive);
    }

}
