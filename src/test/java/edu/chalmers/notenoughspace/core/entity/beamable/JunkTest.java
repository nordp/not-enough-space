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

    @Test
    public void getPlanetaryInhabitant() throws Exception {
        assertNull(junk.getPlanetaryInhabitant());
        junk.setPlanetaryInhabitant(body);
        assertEquals(body,junk.getPlanetaryInhabitant());
        assertNotEquals(body.clone(),junk.getPlanetaryInhabitant());
    }

    @Test
    public void setPlanetaryInhabitant() throws Exception {
        junk.setPlanetaryInhabitant(null);
        assertNull(junk.getPlanetaryInhabitant());
        junk.setPlanetaryInhabitant(body);
        assertEquals(body,junk.getPlanetaryInhabitant());
        assertNotEquals(body.clone(),junk.getPlanetaryInhabitant());
    }

}