package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by Vibergf on 03/04/2017.
 */
public class BeamControl extends AbstractControl {
    protected void controlUpdate(float v) {
        spatial.rotate(0f, 2f * v, 0f);
    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
