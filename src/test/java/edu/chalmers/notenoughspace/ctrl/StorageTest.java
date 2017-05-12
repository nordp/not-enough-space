package edu.chalmers.notenoughspace.ctrl;

import edu.chalmers.notenoughspace.core.Storage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by juliaortheden on 2017-05-12.
 */
public class StorageTest {

    @Test
    public void StorageTest() throws Exception{
        Storage storage = new Storage();

        //TODO: add score to compare with
        assertEquals(storage.calculateScore(), 100);

        //TODO: add weight to compare with
        assertEquals(storage.calculateWeight(), 100);



    }
}
