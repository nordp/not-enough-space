package edu.chalmers.notenoughspace.model;

import edu.chalmers.notenoughspace.nodes.Beamable;

import java.util.LinkedList;
import java.util.*;

/**
 * Created by juliaortheden on 2017-04-06.
 */
public class Storage {

    private List<Beamable> beamableList = new LinkedList();

    public int calculateScore(){
        return calculateWeight();       //TODO Temporary scoring system
    }

    public int calculateWeight() {
        int weight = 0;
        for (int i = 0; i < beamableList.size(); i++) {
            weight += beamableList.get(i).getWeight();
        }
     return weight;
    }
}
