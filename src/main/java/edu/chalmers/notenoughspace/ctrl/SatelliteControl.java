package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;

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
        boolean colliding = ControlUtil.checkCollision(((Node) spatial).getChild(0), (ControlUtil.getRoot(spatial).getChild("shipModel")));

        if (colliding) {
            satellite.collision();
        }
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
