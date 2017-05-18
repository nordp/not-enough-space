package edu.chalmers.notenoughspace.util;

import com.jme3.scene.Node;
import org.junit.Test;

import static edu.chalmers.notenoughspace.ctrl.ControlUtil.getRoot;
import static org.junit.Assert.*;

/**
 * Created by Phnor on 2017-04-26.
 */
public class ControlUtilTest {
    @Test
    public void getRootTest() throws Exception {
        Node initial = new Node();
        Node parent = initial;
        Node child = null;
        for (int i = 0; i < 20; i++){
            child = new Node();
            parent.attachChild(child);
            parent = child;
        }


        assertTrue(child != null);
        assertTrue(getRoot(child) == initial);
        assertTrue(getRoot(parent) == initial);
        assertTrue(child != initial);
        assertTrue(parent != initial);
    }

}