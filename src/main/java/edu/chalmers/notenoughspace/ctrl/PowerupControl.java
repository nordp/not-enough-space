package edu.chalmers.notenoughspace.ctrl;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.entity.powerup.Powerup;

/**
 * Control responsible for telling the power-up when to update and
 * for notifying it when it collides with the ship.
 */
public class PowerupControl extends DetachableControl {

    private final Powerup powerup;

    public PowerupControl(Powerup powerup){
        this.powerup = powerup;
    }


    protected void controlUpdate(float tpf) {
        powerup.update(tpf);
        checkCollisionWithShip();
    }


    private void checkCollisionWithShip() {
        Spatial shipModel = ControlUtil.getRoot(spatial).getChild("shipModel");
        boolean colliding = ControlUtil.checkCollision(getModel(), shipModel);

        if (colliding) {
            powerup.collision();
        }
    }

    private Spatial getModel() {
        return ((Node) spatial).getChild(0);
    }

}
