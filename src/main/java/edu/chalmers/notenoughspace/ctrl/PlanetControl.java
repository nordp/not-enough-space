package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.entity.Planet;

/**
 * Created by Phnor on 2017-05-08.
 */
public class PlanetControl extends DetachableControl {
    private Planet planet;

    public PlanetControl(Planet planet) {
        this.planet = planet;
    }

    protected void controlUpdate(float v) {

        //spatial.setLocalTransform(planet.getTranslation()); TODO
    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
