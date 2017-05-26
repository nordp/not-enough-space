package edu.chalmers.notenoughspace.core.entity.beamable;

import edu.chalmers.notenoughspace.core.entity.beamable.Junk;
import edu.chalmers.notenoughspace.ctrl.TestInhabitant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Phnor on 2017-05-13.
 */
public class JunkTest {
    TestInhabitant body;
    Junk junk;

    @Before
    public void setUp() throws Exception {
        junk = new Junk();
        body = new TestInhabitant(0,0,0);
    }

}