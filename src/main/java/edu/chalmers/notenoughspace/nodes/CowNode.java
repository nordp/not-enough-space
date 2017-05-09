package edu.chalmers.notenoughspace.nodes;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Created by Phnor on 2017-05-09.
 */

public class CowNode extends Node {

    Node shipNode;

    public CowNode(Node shipNode){
        this.shipNode = shipNode;
    }

    public void walk(float speed, float direction, float tpf) {

        rotate(speed*tpf,direction,0);

    }

    public Vector3f getShipDistanceVec() {
        Vector3f shipLocation = shipNode.getChild(0).getWorldTranslation();
        return getChild(0).getWorldTranslation().subtract(shipLocation);
    }
}
