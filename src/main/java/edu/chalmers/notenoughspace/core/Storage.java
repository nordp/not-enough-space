package edu.chalmers.notenoughspace.core;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.event.BeamableStoredEvent;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.StorageChangeEvent;

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

    public float calculateScore(){
            float point = 0;
            for (int i = 0; i < beamableEntityList.size(); i++) {
                point += beamableEntityList.get(i).getPoints();
            }
            return point;
        }


    public float calculateWeight() {
        float weight = 0;
        for (int i = 0; i < beamableEntityList.size(); i++) {
            weight += beamableEntityList.get(i).getWeight();
        }
     return weight;
    }

    @Subscribe
    public void entityStored(BeamableStoredEvent event) {
        if (!beamableEntityList.contains(event.getBeamableEntity())) {
            beamableEntityList.add(event.getBeamableEntity());
            Bus.getInstance().post(new StorageChangeEvent(this));
        }
    }
}
