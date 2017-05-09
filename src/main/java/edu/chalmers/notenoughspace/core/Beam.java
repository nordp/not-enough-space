package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.event.BeamCollisionEvent;
import edu.chalmers.notenoughspace.event.BeamToggleEvent;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phnor on 2017-05-09.
 */
public class Beam implements Entity {
    private boolean active = true;

    private List<Beamable> objectsInBeam;

    public Beam(Entity parent) {
//            super(parent);
        objectsInBeam = new ArrayList<Beamable>();
        Bus.getInstance().register(this);
        Bus.getInstance().post(new EntityCreatedEvent(this));

        setActive(false);
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

    public void update() {

    }

    public void setActive(boolean active) {
        if(this.active == active){
            return;
        }
        this.active = active;
        Bus.getInstance().post(new BeamToggleEvent(active));
    }

    public boolean isActive() {
        return active;
    }
}