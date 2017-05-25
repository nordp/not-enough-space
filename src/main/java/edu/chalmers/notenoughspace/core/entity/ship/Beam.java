package edu.chalmers.notenoughspace.core.entity.ship;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.core.entity.beamable.BeamableEntity;
import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phnor on 2017-05-09.
 */
public class Beam extends Entity {
    private static final float DISTANCE_WHEN_STORED = 0.6f;
    private static final float CENTERING_FORCE = 0.08f;
    private static final float BEAMING_FORCE = 1.0f;

    public static final float ENERGY_COST = 10f;

    private boolean active = true;

    private List<BeamableEntity> objectsInBeam;


    public Beam(Entity parent) {
//            super(parent);
        super(new ZeroGravityStrategy());
        objectsInBeam = new ArrayList<BeamableEntity>();
        Bus.getInstance().register(this);
        Bus.getInstance().post(new EntityCreatedEvent(this));

        setActive(false);
    }

    public synchronized void update(PlanetaryInhabitant shipBody, float tpf) {
        if (isActive()) {
            List<BeamableEntity> objectsToExit = new ArrayList<BeamableEntity>();
            for (BeamableEntity b : objectsInBeam) {
                if (beamEntity(b, shipBody, tpf)){
                    objectsToExit.add(b);
                }
            }

            for (BeamableEntity b : objectsToExit) {
                b.exitBeam();
            }
        }
    }

    @Subscribe
    public synchronized void addToBeam(BeamEnteredEvent event) {
        BeamableEntity beamable = event.getBeamable();
        if (!objectsInBeam.contains(beamable)) {
            objectsInBeam.add(beamable);
        }

    }

    @Subscribe
    public synchronized void removeFromBeam(BeamExitedEvent event) {
        BeamableEntity beamable = event.getBeamableEntity();
        if (objectsInBeam.contains(beamable)) {
            objectsInBeam.remove(beamable);
        }
    }

    //Helper

    /**
     *
     * @param b entity to beam
     * @param shipBody the planetaryinhabitant to beam towards.
     * @return
     * true if beam complete
     */
    private synchronized boolean beamEntity(BeamableEntity b, PlanetaryInhabitant shipBody, float tpf) {
        PlanetaryInhabitant inhabitant = b.getPlanetaryInhabitant();
        float currentHeight = inhabitant.getDistanceToPlanetsCenter();

        if (currentHeight > Planet.PLANET_RADIUS + Ship.ALTITUDE - DISTANCE_WHEN_STORED) {
            Bus.getInstance().post(new BeamableStoredEvent(b));
            return true;
        }
        inhabitant.setDistanceToPlanetsCenter(currentHeight + BEAMING_FORCE * tpf * (1/b.getWeight()));

        //Don't centralise more if already centralised:
//        if (inhabitant.distance(shipBody) < 0.8f) {
//            return false;
//        } else {
            float hypotenuse = inhabitant.distance(shipBody);
            float yDistance = shipBody.getLocalTranslation().y - inhabitant.getLocalTranslation().y;
            float xDistance = (float) Math.sqrt(hypotenuse * hypotenuse - yDistance * yDistance);

            if (xDistance < 0.1f) {
                return false;
            }
//        }

        //Centralise in beam:
        PlanetaryInhabitant left = inhabitant.clone();
        PlanetaryInhabitant right = inhabitant.clone();
        PlanetaryInhabitant forward = inhabitant.clone();
        PlanetaryInhabitant back = inhabitant.clone();

        left.rotateForward(CENTERING_FORCE * tpf);
        right.rotateForward(-CENTERING_FORCE * tpf);
        forward.rotateSideways(CENTERING_FORCE * tpf);
        back.rotateSideways(-CENTERING_FORCE * tpf);


        if (left.distance(shipBody) < right.distance(shipBody)){
            inhabitant.rotateForward(CENTERING_FORCE * tpf);
        } else {
            inhabitant.rotateForward(-CENTERING_FORCE * tpf);
        }

        if (forward.distance(shipBody) < back.distance(shipBody)){
            inhabitant.rotateSideways(CENTERING_FORCE * tpf);
        } else {
            inhabitant.rotateSideways(-CENTERING_FORCE * tpf);
        }

        return false;
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

    public String getID(){ return "beam"; }

    //For testing
    public int getNumberOfObjectsInBeam() {
        return objectsInBeam.size();
    }
}