package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.entity.enemy.Hayfork;

/**
 * Control responsible for telling the hayfork when to update and
 * for notifying it when it collides with another object.
 */
public class HayforkControl extends DetachableControl {

    private Hayfork hayfork;

    public HayforkControl(Hayfork hayfork) {
        this.hayfork = hayfork;
    }


    protected void controlUpdate(float tpf) {
        JMEInhabitant ship = ControlUtil.getShip(spatial);
        Spatial shipModel = ControlUtil.getRoot(spatial).getChild("shipModel");
        boolean colliding = ControlUtil.checkCollision(getModel(), shipModel);

        hayfork.update(ship, tpf);

        if (colliding) {
            hayfork.hitSomething();
        }
    }


    private Spatial getModel() {
        return ((Node) spatial).getChild(0);
    }

}
