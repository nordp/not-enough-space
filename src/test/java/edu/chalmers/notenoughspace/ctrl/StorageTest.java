package edu.chalmers.notenoughspace.ctrl;

import edu.chalmers.notenoughspace.core.Cow;
import edu.chalmers.notenoughspace.core.Storage;
import edu.chalmers.notenoughspace.event.BeamableStoredEvent;
import edu.chalmers.notenoughspace.event.Bus;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by juliaortheden on 2017-05-12.
 */
public class StorageTest {

    @Test
    public void StorageTest() throws Exception{
        Storage storage = new Storage();

        assertEquals(0, storage.calculateScore(), 0);
        assertEquals(0, storage.calculateWeight(), 0);


        List<Cow> cowList = new ArrayList<Cow>();

        for(int i = 0; i<10; i++) {
            Cow cow = new Cow();
            cowList.add(cow);
            Bus.getInstance().post(new BeamableStoredEvent(cow));
        }

        float weight = 0;
        float point = 0;
        for ( int i = 0; i<cowList.size(); i++){
            weight+=cowList.get(i).getWeight();
            point+=cowList.get(i).getPoints();
        }

        assertEquals(storage.calculateScore(), point, 0);
        assertEquals(storage.calculateWeight(), weight, 0);
    }
}
