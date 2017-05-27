package edu.chalmers.notenoughspace.ctrl;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.TestUtil;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;
import org.junit.Test;

import javax.vecmath.Vector3f;

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
        PlanetaryInhabitant jme = new JMEInhabitant(node);
        PlanetaryInhabitant test = new TestInhabitant(0f, 1f, 0f);

        assertNotNull(jme);
        assertNotNull(test);
        assertEquals(jme.getPosition(), test.getPosition());

        PlanetaryInhabitant jmeClone = jme.clone();
        PlanetaryInhabitant testClone = test.clone();
        assertNotEquals(jme, jmeClone);
        assertNotEquals(test, testClone);
        assertEquals(jme.getPosition(), jmeClone.getPosition());
        assertEquals(test.getPosition(), testClone.getPosition());

        jmeClone.rotateForward(1f);
        testClone.rotateForward(1f);
        assertNotEquals(jme.getPosition(), jmeClone.getPosition());
        assertNotEquals(test.getPosition(), testClone.getPosition());
        TestUtil.assertVectorEquals(jmeClone.getPosition(), testClone.getPosition(), 0.0001f);

        test.move(new Vector3f(0f, 1f, 0f));
        assertEquals(1f, jme.distanceTo(test), 0f);
        assertEquals(jme.distanceTo(test), test.distanceTo(jme), 0f);

        jme.move(new Vector3f(0f, 1f, 0f));
        jme.rotateSideways(2f);
        test.rotateSideways(2f);
        TestUtil.assertVectorEquals(jme.getPosition(), test.getPosition(), 0.0001f);

        jme.setDistanceFromPlanetsCenter(10f);
        test.setDistanceFromPlanetsCenter(10f);
        assertEquals(10f, jme.getDistanceFromPlanetsCenter(), 0f);
        assertEquals(10f, test.getDistanceFromPlanetsCenter(), 0f);


    }
}
