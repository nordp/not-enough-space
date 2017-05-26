package edu.chalmers.notenoughspace.core.entity;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.core.entity.beamable.BeamableEntity;
import edu.chalmers.notenoughspace.core.entity.beamable.Cow;
import edu.chalmers.notenoughspace.core.entity.beamable.Junk;
import edu.chalmers.notenoughspace.core.entity.enemy.Farmer;
import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;
import edu.chalmers.notenoughspace.core.entity.powerup.Powerup;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the planet where the game takes place and keeps track of the entities inhabiting it.
 */
public class Planet extends Entity {

    public static final float PLANET_RADIUS = 13f;

    private List<Entity> population;

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

    public void addInhabitant(Entity entity) {
        population.add(entity);
    }

    public void randomizeInhabitantPositions() {
        for(Entity e : population){
            Entity.randomizePosition(e.getPlanetaryInhabitant());
        }
    }

    @Subscribe
    public void satelliteCollision(SatelliteCollisionEvent event){
        Satellite satellite = event.getSatellite();
        population.remove(satellite);
        Bus.getInstance().post(new EntityRemovedEvent(satellite));
    }

    @Subscribe
    public void powerupCollision(PowerupCollisionEvent event){
        Powerup powerup = event.getPowerup();
        population.remove(powerup);
        Bus.getInstance().post(new EntityRemovedEvent(powerup));
    }

    @Subscribe
    public void beamableStored(BeamableStoredEvent event){
        BeamableEntity beamable = event.getBeamableEntity();
        population.remove(beamable);
        Bus.getInstance().post(new EntityRemovedEvent(beamable));
    }

    public String getID() { return "planet"; }

}
