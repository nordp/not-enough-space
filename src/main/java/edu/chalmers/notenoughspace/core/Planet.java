package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.AttachedEvent;
import edu.chalmers.notenoughspace.event.Bus;

import java.util.ArrayList;

/**
 * Created by Vibergf on 25/04/2017.
 */
public class Planet implements Spatial3D{

    public final static float PLANET_RADIUS = 13f;

    private ArrayList<Spatial3D> population;

    public Planet(Spatial3D parent){
//        super(parent);
        population = new ArrayList<Spatial3D>();
    }

    public void fireEvent(Spatial3D parent) {
        Bus.getInstance().post(new AttachedEvent(parent, this, true));
    }

    public void update() {

    }

    public void populate(int nCow, int nJunk, int nSatellite){
//        children.clear();
        for (int i = 0; i < nCow; i++){
            Spatial3D c = new Cow(this);

            //TODO Implement random placing
            //c.rotate(i,i,i);
        }

        for (int i = 0; i < nJunk; i++){
            new Junk(this);
        }

        for (int i = 0; i < nSatellite; i++){
            new Satellite(this);
        }
    }
}
