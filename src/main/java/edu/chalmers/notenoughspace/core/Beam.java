package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.event.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phnor on 2017-05-09.
 */
public class Beam extends Entity {
    private static final float DISTANCE_WHEN_STORED = 0.6f;
    private static final float CENTERING_FORCE = 0.001f;

    private boolean active = true;

    private List<BeamableEntity> objectsInBeam;


    public Beam(Entity parent) {
//            super(parent);
        objectsInBeam = new ArrayList<BeamableEntity>();
        Bus.getInstance().register(this);
        Bus.getInstance().post(new EntityCreatedEvent(this));

        setActive(false);
    }

    public void update(PlanetaryInhabitant shipBody) {
        if (isActive()) {
            for (BeamableEntity b : objectsInBeam) {
                liftEntityTowardsShip(b, shipBody);
            }
        }
    }

    @Subscribe
    public void addToBeam(BeamEnteredEvent event) {
        BeamableEntity beamable = event.getBeamable();
        objectsInBeam.add(beamable);
    }

    @Subscribe
    public void removeFromBeam(BeamExitedEvent event) {
        BeamableEntity beamable = event.getBeamableEntity();
        objectsInBeam.remove(beamable);
    }

    //Helper
    private void liftEntityTowardsShip(BeamableEntity b, PlanetaryInhabitant shipBody) {
        PlanetaryInhabitant inhabitant = b.getPlanetaryInhabitant();
        float currentHeight = inhabitant.getLocalTranslation().y;

        if (currentHeight > Planet.PLANET_RADIUS + Ship.ALTITUDE - DISTANCE_WHEN_STORED) {
            Bus.getInstance().post(new BeamableStoredEvent(b));
            return;
        }
        inhabitant.setDistanceToPlanetsCenter(currentHeight + 0.01f);

        //Don't centralise more if already centralised:
        if (inhabitant.distance(shipBody) < 0.8f) {
            return;
        } else {
            float hypotenuse = inhabitant.distance(shipBody);
            float yDistance = shipBody.getLocalTranslation().y - inhabitant.getLocalTranslation().y;
            float xDistance = (float) Math.sqrt(hypotenuse * hypotenuse - yDistance * yDistance);

            if (xDistance < 0.1f) {
                return;
            }
        }

        //Centralise in beam:
        PlanetaryInhabitant left = inhabitant.clone();
        PlanetaryInhabitant right = inhabitant.clone();
        PlanetaryInhabitant forward = inhabitant.clone();
        PlanetaryInhabitant back = inhabitant.clone();

        left.rotateForward(CENTERING_FORCE);
        right.rotateForward(-CENTERING_FORCE);
        forward.rotateSideways(CENTERING_FORCE);
        back.rotateSideways(-CENTERING_FORCE);


        if (left.distance(shipBody) < right.distance(shipBody)){
            inhabitant.rotateForward(CENTERING_FORCE);
        } else {
            inhabitant.rotateForward(-CENTERING_FORCE);
        }

        if (forward.distance(shipBody) < back.distance(shipBody)){
            inhabitant.rotateSideways(CENTERING_FORCE);
        } else {
            inhabitant.rotateSideways(-CENTERING_FORCE);
        }

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
}