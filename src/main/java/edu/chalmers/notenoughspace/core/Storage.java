package edu.chalmers.notenoughspace.core;

import java.util.LinkedList;
import java.util.*;

/**
 * Created by juliaortheden on 2017-04-06.
 */
public class Storage {

    private List<BeamableEntity> beamableEntityList = new LinkedList();

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
}
