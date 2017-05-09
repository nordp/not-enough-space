package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.event.AttachedEvent;
import edu.chalmers.notenoughspace.event.BeamCollisionEvent;
import edu.chalmers.notenoughspace.event.Bus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vibergf on 25/04/2017.
 */
public class Ship implements Spatial3D {

    public static final float SPEED = 1;
    public static final float ROTATION_SPEED = 2;

    private int energy;
    public Beam beam;

    public Ship(Spatial3D parent){
//        super(parent);
        energy = 100;
        beam = new Beam(this);
//        detachChild(beam);

        //beamNode.setLocalTranslation(beamNode.getLocalTranslation().add(this.getChild("ship").getLocalTranslation()));
    }

    public void fireEvent(Spatial3D parent) {
        Bus.getInstance().post(new AttachedEvent(parent, this, true));
    }

    public void update() {

    }

    public boolean isBeamActive() {
        return beam.beamActive;
    }

    public void setBeamActive(boolean beamActive) {
        beam.beamActive = beamActive;
    }

    private class Beam implements Spatial3D{
        private boolean beamActive = false;

        private List<Beamable> objectsInBeam;

        public Beam(Spatial3D parent) {
//            super(parent);
            objectsInBeam = new ArrayList<Beamable>();
            Bus.getInstance().register(this);
        }

        @Subscribe
        public void beamCollisionEvent(BeamCollisionEvent event){
            List<Beamable> newObjectsInBeam = event.collidingObjects;
            for(Beamable b : objectsInBeam) {
                if(!newObjectsInBeam.contains(b)) {
                    b.setInBeam(BeamState.NOT_IN_BEAM);
                    //TODO Fire spatial event?
                }
            }
            for(Beamable b : newObjectsInBeam) {
                if(!objectsInBeam.contains(b)) {
                    b.setInBeam(BeamState.IN_BEAM);
                    //TODO Fire spatial event?
                }
            }
            objectsInBeam = newObjectsInBeam;
        }

        public void fireEvent(Spatial3D parent) {
            Bus.getInstance().post(new AttachedEvent(parent, this, true));
        }

        public void update() {

        }

        public void setActive(boolean active) {
            if(beamActive = active){
                return;
            }

            beamActive = active;

            if(active){
//                attachChild(beam);
            }else{
//                detachChild(beam);
            }
        }
    }
}
