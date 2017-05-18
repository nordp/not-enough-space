package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.event.*;

import java.util.ArrayList;

/**
 * Created by Vibergf on 25/04/2017.
 */
public class Planet extends Entity {

    public final static float PLANET_RADIUS = 13f;

    private ArrayList<Entity> population;


    public Planet(){
//        super(parent);
        population = new ArrayList<Entity>();
        Bus.getInstance().post(new EntityCreatedEvent(this));
        Bus.getInstance().register(this);
    }

    public void update() {

    }

    public void populate(int nCow, int nJunk, int nSatellite, int nFarmer){
//        children.clear();
        for (int i = 0; i < nCow; i++){
            Entity c = new Cow();

            //TODO Implement random placing
            //c.rotate(i,i,i);
        }

        for (int i = 0; i < nJunk; i++){
            new Junk();
        }

        for (int i = 0; i < nSatellite; i++){
            new Satellite();
        }

        for (int i = 0; i < nFarmer; i++){
            new Farmer();
        }
    }

    @Subscribe
    public void satelliteCollision(SatelliteCollisionEvent event){
        population.remove(event.getSatellite());
        Bus.getInstance().post(new EntityRemovedEvent(event.getSatellite()));
    }

    @Subscribe
    public void beamableStored(BeamableStoredEvent event){
        population.remove(event.getBeamableEntity());
        Bus.getInstance().post(new EntityRemovedEvent(event.getBeamableEntity()));
    }

    public String getID() { return "planet"; }
}
