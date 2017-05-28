package edu.chalmers.notenoughspace.core.entity;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the planet where the game takes place and keeps track of the entities inhabiting it.
 */
public class Planet extends Entity {

    public static final float PLANET_RADIUS = 13f;

    private final List<Entity> population;

    public Planet(){
        super(new ZeroGravityStrategy());
        population = new ArrayList<Entity>();

        Bus.getInstance().post(new EntityCreatedEvent(this));
        Bus.getInstance().register(this);
    }


    public void cleanup() {
        for(Entity e : population){
            Bus.getInstance().post(new EntityRemovedEvent(e));
        }

        population.clear();

        Bus.getInstance().unregister(this);
        Bus.getInstance().post(new EntityRemovedEvent(this));
    }

    public void addInhabitant(Entity inhabitant) {
        population.add(inhabitant);
    }

    private void removeInhabitant(Entity inhabtitant){
        population.remove(inhabtitant);
        Bus.getInstance().post(new EntityRemovedEvent(inhabtitant));
    }

    public void randomizeInhabitantPositions() {
        for(Entity e : population){
            e.randomizePosition();
        }
    }

    @Subscribe
    public void satelliteCollision(SatelliteCollisionEvent event){
        removeInhabitant(event.getSatellite());
    }

    @Subscribe
    public void powerupCollision(PowerupCollisionEvent event){
        removeInhabitant(event.getPowerup());
    }

    @Subscribe
    public void beamableStored(BeamableStoredEvent event){
        removeInhabitant(event.getBeamable());
    }

    public String getID() { return "planet"; }

}
