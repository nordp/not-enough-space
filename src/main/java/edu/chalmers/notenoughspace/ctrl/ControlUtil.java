package edu.chalmers.notenoughspace.ctrl;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;

/**
 * Utility class for control related tasks.
 */
class ControlUtil {

    private static PlanetaryInhabitant ship;

    private ControlUtil(){}


    public static Node getRoot(Spatial spatial){
        if (spatial == null)
            throw new IllegalArgumentException("Spatial equals null.");

        while (spatial.getParent() != null){
            spatial = spatial.getParent();
        }
        return (Node) spatial;
    }

    public static boolean checkCollision(Spatial first, Spatial second){
        CollisionResults results = new CollisionResults();
        BoundingVolume boundingVolume = first.getWorldBound();

        second.collideWith(boundingVolume, results);

        return results.size() > 0;
    }

}
