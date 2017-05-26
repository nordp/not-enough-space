package edu.chalmers.notenoughspace.ctrl;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vibergf on 11/05/2017.
 */
public class InhabitantTest {

    @Test
    public void inhabitantTest(){
//        PlanetaryInhabitant pi = new TestInhabitant(0f, 1f, 0f);

        Node node = new Node();
        Spatial spatial = new Geometry();
        spatial.move(0f, 1f, 0f);
        node.attachChild(spatial);
        PlanetaryInhabitant pi = new JMEInhabitant(node);

        assertNotNull(pi);

        PlanetaryInhabitant clone = pi.clone();
        assertNotEquals(pi, clone);
        assertEquals(pi.getWorldTranslation(), clone.getWorldTranslation());

        clone.rotateForward(1f);
        assertNotEquals(pi.getWorldTranslation(), clone.getWorldTranslation());

        PlanetaryInhabitant pi2 = new TestInhabitant(0f, 2f, 0f);
        assertEquals(1f, pi.distanceTo(pi2), 0f);
    }
}
