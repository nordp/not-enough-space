package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.Farmer;
import edu.chalmers.notenoughspace.core.PlanetaryInhabitant;
import edu.chalmers.notenoughspace.core.Ship;

/**
 * Created by Phnor on 2017-05-13.
 */
public class FarmerControl extends AbstractControl {
    private Farmer farmer;
    private Ship ship;

    public FarmerControl(Farmer farmer){
        this.farmer = farmer;
    }

    protected void controlUpdate(float tpf) {
        farmer.update(new JMEInhabitant(NodeUtil.getRoot(spatial).getChild("ship")), tpf);
        //TODO: Borde ej skapa nytt objekt varje update
    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
