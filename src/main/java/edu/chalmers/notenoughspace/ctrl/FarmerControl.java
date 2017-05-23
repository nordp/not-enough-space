package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.entity.enemy.Farmer;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;

/**
 * Created by Phnor on 2017-05-13.
 */
public class FarmerControl extends DetachableControl {
    private Farmer farmer;
    private Ship ship;

    public FarmerControl(Farmer farmer){
        this.farmer = farmer;
    }

    protected void controlUpdate(float tpf) {
        farmer.update(new JMEInhabitant(ControlUtil.getRoot(spatial).getChild("ship")), tpf);
        //TODO: Borde ej skapa nytt objekt varje update
    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
