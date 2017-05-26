package edu.chalmers.notenoughspace.core.entity;

import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;
import edu.chalmers.notenoughspace.ctrl.TestInhabitant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Vibergf on 26/05/2017.
 */
public class EntityTest {
    Entity e;
    PlanetaryInhabitant body;

    @Before
    public void setUp(){
        e = new TestEntity();
        body = new TestInhabitant(0f, 0f, 0f);
    }

    @Test
    public void planetaryInhabitantTest() throws Exception {
        e.setPlanetaryInhabitant(null);
        assertNull(e.getPlanetaryInhabitant());
        e.setPlanetaryInhabitant(body);
        assertEquals(body, e.getPlanetaryInhabitant());
        assertNotEquals(body.clone(), e.getPlanetaryInhabitant());
    }
}
