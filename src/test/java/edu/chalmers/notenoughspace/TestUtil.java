package edu.chalmers.notenoughspace;


import javax.vecmath.Vector3f;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vibergf on 27/05/2017.
 */
public class TestUtil {

    public static void assertVectorEquals(Vector3f expected, Vector3f actual, float delta){
        assertEquals(expected.x, actual.x, delta);
        assertEquals(expected.y, actual.y, delta);
        assertEquals(expected.z, actual.z, delta);
    }
}
