package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import edu.chalmers.notenoughspace.core.entity.powerup.Powerup;

/**
 * Created by Vibergf on 25/05/2017.
 */
public class PowerupControl extends DetachableControl {

    private Powerup powerup;

    public PowerupControl(Powerup powerup){
        this.powerup = powerup;
    }

    protected void controlUpdate(float v) {
        spatial.rotate(0.005f, 0, 0);

        //Collision
        boolean colliding = ControlUtil.checkCollision(((Node) spatial).getChild(0), (ControlUtil.getRoot(spatial).getChild("shipModel")));

        if (colliding) {
            powerup.collision();
        }
    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
