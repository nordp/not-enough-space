package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;

import java.util.ArrayList;

/**
 * Created by Vibergf on 25/04/2017.
 */
public class Planet implements Entity {

    public final static float PLANET_RADIUS = 13f;

    private ArrayList<Entity> population;

    private PlanetaryInhabitant body;

    public Planet(){
//        super(parent);
        population = new ArrayList<Entity>();

        Bus.getInstance().post(new EntityCreatedEvent(this));
    }

    public void update() {

    }

    public void populate(int nCow, int nJunk, int nSatellite){
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
    }

    public PlanetaryInhabitant getPlanetaryInhabitant() {
        return body;
    }

    public void setPlanetaryInhabitant(PlanetaryInhabitant body) {
        this.body = body;
    }
}
