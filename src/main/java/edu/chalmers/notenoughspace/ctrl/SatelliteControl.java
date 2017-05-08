package edu.chalmers.notenoughspace.ctrl;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by juliaortheden on 2017-04-26.
 */
public class SatelliteControl extends AbstractControl {


    @Override
    protected void controlUpdate(float tpf) {
        spatial.rotate(FastMath.HALF_PI, 0, 0);

    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
