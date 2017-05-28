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
 * The tractor beam of the ship. Drags cows and junk towards the ship when they are inside
 * the beam.
 */
public class Beam extends Entity {

    private static final float BEAMING_FORCE = 1.0f;
    private static final float CENTERING_FORCE = 0.08f;
    private static final float DISTANCE_WHEN_STORED = 0.6f;
    private static final float ENERGY_COST = 10f;
    private final PlanetaryInhabitant beamer;

    private boolean active = true;
    private final List<BeamableEntity> objectsInBeam;

    public Beam(PlanetaryInhabitant beamer) {
        super(new ZeroGravityStrategy());
        this.beamer = beamer;
        objectsInBeam = new ArrayList<BeamableEntity>();

        Bus.getInstance().register(this);
        Bus.getInstance().post(new EntityCreatedEvent(this));

        setActive(false);
    }


    public synchronized void update(float tpf) {
        if (isActive()) {
            List<BeamableEntity> entitiesToMoveToStorage = new ArrayList<BeamableEntity>();

            for (BeamableEntity candidate : objectsInBeam) {
                boolean wasBeamedIntoStorage = beamObject(candidate, beamer, tpf);
                if (wasBeamedIntoStorage){
                    entitiesToMoveToStorage.add(candidate);
                }
            }

            for (BeamableEntity entity : entitiesToMoveToStorage) {
                entity.exitBeam();
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
        BeamableEntity beamable = event.getBeamable();
        if (objectsInBeam.contains(beamable)) {
            objectsInBeam.remove(beamable);
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

    public int getNumberOfObjectsInBeam() {
        return objectsInBeam.size();
    }

    public static float getEnergyCost() {
        return ENERGY_COST;
    }

    /**
     *
     * @param entity Entity to beam.
     * @param beamer The PlanetaryInhabitant to beam towards.
     * @return true if entity is close enough to be moved into storage.
     * Pulls the entity inwards and upwards towards the beaming PlanetaryInhabitant.
     */
    private synchronized boolean beamObject(BeamableEntity entity, PlanetaryInhabitant beamer, float tpf) {
        PlanetaryInhabitant inhabitant = entity.getPlanetaryInhabitant();
        float currentHeight = inhabitant.getDistanceFromPlanetsCenter();

        if (reachedStorageAltitude(currentHeight)) {
            Bus.getInstance().post(new BeamableStoredEvent(entity));

            return true;
        } else {
            lift(inhabitant, currentHeight, entity.getWeight(), tpf);
            pullTowardsBeamsCenter(inhabitant, beamer, tpf);

            return false;
        }
    }

    private boolean reachedStorageAltitude(float currentHeight) {
        float minimumStorageAltitude = Planet.PLANET_RADIUS + Ship.ALTITUDE - DISTANCE_WHEN_STORED;
        return currentHeight >= minimumStorageAltitude;
    }

    private void lift(PlanetaryInhabitant inhabitant, float currentHeight, float weight, float tpf) {
        float verticalLift = BEAMING_FORCE * tpf / weight;
        float newHeight = currentHeight + verticalLift;
        inhabitant.setDistanceFromPlanetsCenter(newHeight);
    }

    private void pullTowardsBeamsCenter(PlanetaryInhabitant inhabitant, PlanetaryInhabitant beamer, float tpf) {
        if (atCenter(inhabitant, beamer)) {
            return;
        }

        PlanetaryInhabitant left = inhabitant.clone();
        PlanetaryInhabitant right = inhabitant.clone();
        PlanetaryInhabitant forward = inhabitant.clone();
        PlanetaryInhabitant back = inhabitant.clone();

        left.rotateForward(CENTERING_FORCE * tpf);
        right.rotateForward(-CENTERING_FORCE * tpf);
        forward.rotateSideways(CENTERING_FORCE * tpf);
        back.rotateSideways(-CENTERING_FORCE * tpf);

        if (left.distanceTo(beamer) < right.distanceTo(beamer)){
            inhabitant.rotateForward(CENTERING_FORCE * tpf);
        } else {
            inhabitant.rotateForward(-CENTERING_FORCE * tpf);
        }

        if (forward.distanceTo(beamer) < back.distanceTo(beamer)){
            inhabitant.rotateSideways(CENTERING_FORCE * tpf);
        } else {
            inhabitant.rotateSideways(-CENTERING_FORCE * tpf);
        }
    }

    private boolean atCenter(PlanetaryInhabitant inhabitant, PlanetaryInhabitant beamer) {
        float hypotenuse = inhabitant.distanceTo(beamer);
        float yDistance = beamer.getDistanceFromPlanetsCenter() - inhabitant.getDistanceFromPlanetsCenter();
        float hSquared = hypotenuse * hypotenuse;
        float ySquared = yDistance * yDistance;
        float xDistance = (float) Math.sqrt(hSquared - ySquared);

        return xDistance < 0.1f;
    }

}