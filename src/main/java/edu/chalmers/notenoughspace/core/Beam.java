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

    private List<BeamableEntity> objectsInBeam;

    private PlanetaryInhabitant body;

    public Beam(Entity parent) {
//            super(parent);
        objectsInBeam = new ArrayList<BeamableEntity>();
        Bus.getInstance().register(this);
        Bus.getInstance().post(new EntityCreatedEvent(this));

        setActive(false);
    }

    @Subscribe
    public void beamCollisionEvent(BeamCollisionEvent event){
        List<BeamableEntity> newObjectsInBeam = event.collidingObjects;
        for(BeamableEntity b : objectsInBeam) {
            if(!newObjectsInBeam.contains(b)) {
                b.setInBeam(BeamState.NOT_IN_BEAM);
                //TODO Fire spatial event?
            }
        }
        for(BeamableEntity b : newObjectsInBeam) {
            if(!objectsInBeam.contains(b)) {
                b.setInBeam(BeamState.IN_BEAM);
                //TODO Fire spatial event?
            }
        }
        objectsInBeam = newObjectsInBeam;
    }

    public void update() {
        if (isActive()) {
            for (BeamableEntity b : objectsInBeam) {
                liftEntityTowardsShip(b);
            }
        }
    }

    //Helper
    private void liftEntityTowardsShip(BeamableEntity b) {
        PlanetaryInhabitant inhabitant = b.getPlanetaryInhabitant();
        float currentHeight = inhabitant.getLocalTranslation().y;

        if (currentHeight > Planet.PLANET_RADIUS + Ship.ALTITUDE) {
            //TODO: Remove beamed object from world and put into storage.
            //TODO: EntityBeamedEvent?
            return;
        }

        inhabitant.setDistanceToPlanetsCenter(currentHeight + 0.01f);
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

    public PlanetaryInhabitant getPlanetaryInhabitant() {
        return body;
    }

    public void setPlanetaryInhabitant(PlanetaryInhabitant body) {
        this.body = body;
    }
}