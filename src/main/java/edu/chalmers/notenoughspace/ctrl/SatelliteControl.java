package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.nodes.SatelliteNode;

/**
 * Created by juliaortheden on 2017-04-26.
 */
public class SatelliteControl extends AbstractControl {


    @Override
    protected void controlUpdate(float tpf) {
        spatial.rotate(0.01f, 0, 0);

        // TODO satellite.explodeWhenCollision();
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
