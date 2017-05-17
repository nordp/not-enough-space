package edu.chalmers.notenoughspace.ctrl;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.Satellite;

/**
 * Created by juliaortheden on 2017-04-26.
 */
public class SatelliteControl extends AbstractControl {

    private Satellite satellite;

    public SatelliteControl(Satellite satellite){
        this.satellite = satellite;
    }


    @Override
    protected void controlUpdate(float tpf) {
        spatial.rotate(0.01f, 0, 0);

        //Collision
        if(((Node)spatial).getChildren().size() > 0) { //TEMPORARY, remove when proper explosion handling is implemented
            CollisionResults results = new CollisionResults();
            BoundingVolume bv = ((Node) spatial).getChild(0).getWorldBound();
            (NodeUtil.getRoot(spatial).getChild("shipModel")).collideWith(bv, results);

            if (results.size() > 0) {
                ((Node) spatial).detachAllChildren();
            }
        }
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
