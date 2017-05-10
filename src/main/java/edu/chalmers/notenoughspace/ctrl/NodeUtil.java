package edu.chalmers.notenoughspace.ctrl;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Utility class for jme-nodes
 *
 * Created by Phnor on 2017-04-26.
 */
public class NodeUtil {
    private NodeUtil(){}

    public static Node getRoot(Spatial spatial){
        if (spatial == null)
            throw new IllegalArgumentException("Spatial == Null");
        while (spatial.getParent() != null){
            spatial = spatial.getParent();
        }
        return (Node) spatial;
    }
}
