package edu.chalmers.notenoughspace.ctrl;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Utility class for control related tasks.
 */
public class ControlUtil {

    private static JMEInhabitant ship;

    private ControlUtil(){}


    public static Node getRoot(Spatial spatial){
        if (spatial == null)
            throw new IllegalArgumentException("Spatial equals null.");

        while (spatial.getParent() != null){
            spatial = spatial.getParent();
        }
        return (Node) spatial;
    }

    public static JMEInhabitant getShip(Spatial spatial) {
        if (ship == null) {
            Node rootNode = getRoot(spatial);
            Spatial shipSpatial = rootNode.getChild("ship");
            ship = new JMEInhabitant(shipSpatial);
        }

        return ship;
    }

    public static boolean checkCollision(Spatial first, Spatial second){
        CollisionResults results = new CollisionResults();
        BoundingVolume boundingVolume = first.getWorldBound();

        second.collideWith(boundingVolume, results);
        boolean colliding = results.size() > 0;

        return colliding;
    }

}
