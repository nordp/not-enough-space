package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * Extension of AbstractControl, with possibility to perform action when the control is detached.
 */
public abstract class DetachableControl extends AbstractControl {

    public void onDetach(){}    //To be overridden if needed.

    protected void controlUpdate(float v) {}

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {}

}
