package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;

/**
 * Control responsible for telling the satellite when to update and
 * for notifying it when it collides with the ship.
 */
public class SatelliteControl extends DetachableControl {

    private Satellite satellite;

    public SatelliteControl(Satellite satellite){
        this.satellite = satellite;
    }


    @Override
    protected void controlUpdate(float tpf) {
        satellite.update(tpf);
        checkCollisionWithShip();
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }


    private void checkCollisionWithShip() {
        Spatial shipModel = ControlUtil.getRoot(spatial).getChild("shipModel");
        boolean colliding = ControlUtil.checkCollision(getModel(), shipModel);

        if (colliding) {
            satellite.collision();
        }
    }

    private Spatial getModel() {
        return ((Node) spatial).getChild(0);
    }

}
