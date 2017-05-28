package edu.chalmers.notenoughspace.ctrl;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.entity.enemy.Hayfork;

/**
 * Control responsible for telling the hayfork when to update and
 * for notifying it when it collides with the ship.
 */
public class HayforkControl extends DetachableControl {

    private final Hayfork hayfork;

    public HayforkControl(Hayfork hayfork) {
        this.hayfork = hayfork;
    }


    protected void controlUpdate(float tpf) {
        JMEInhabitant ship = ControlUtil.getShip(spatial);

        hayfork.update(ship, tpf);
        checkCollisionWithShip();
    }


    private void checkCollisionWithShip() {
        Spatial shipModel = ControlUtil.getRoot(spatial).getChild("shipModel");
        boolean colliding = ControlUtil.checkCollision(getModel(), shipModel);

        if (colliding) {
            hayfork.hitSomething();
        }
    }

    private Spatial getModel() {
        return ((Node) spatial).getChild(0);
    }

}
