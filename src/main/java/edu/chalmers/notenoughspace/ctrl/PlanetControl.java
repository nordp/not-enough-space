package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.Planet;
import edu.chalmers.notenoughspace.core.Spatial3D;

/**
 * Created by Phnor on 2017-05-08.
 */
public class PlanetControl extends AbstractControl {
    private Planet planet;

    public PlanetControl(Planet planet) {
        this.planet = planet;
    }

    protected void controlUpdate(float v) {
        planet.update();
        //spatial.setLocalTransform(planet.getTranslation()); TODO
    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
