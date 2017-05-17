package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityStoredEvent;

import java.util.LinkedList;
import java.util.*;

/**
 * Created by juliaortheden on 2017-04-06.
 */
public class Storage {

    private List<BeamableEntity> beamableEntityList = new LinkedList();

    public Storage() {
        Bus.getInstance().register(this);
    }

    public int calculateScore(){
        return calculateWeight();       //TODO Temporary scoring system
    }

    public int calculateWeight() {
        int weight = 0;
        for (int i = 0; i < beamableEntityList.size(); i++) {
            weight += beamableEntityList.get(i).getWeight();
        }
     return weight;
    }

    @Subscribe
    public void entityStored(EntityStoredEvent event) {
        if (!beamableEntityList.contains(event.getBeamableEntity())) {
            beamableEntityList.add(event.getBeamableEntity());
            System.out.println("Object added to Storage.");
        }
    }
}
