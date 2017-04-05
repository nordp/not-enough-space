package edu.chalmers.notenoughspace;

import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class ShipOverPlanetControl extends AbstractControl {

    public ShipOverPlanetControl() {
    }

    protected void controlUpdate(float v) {

    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }

    /**
     * Moves the ship model from its original position at the center of the Ship node
     * to it's correct starting position over the planet's surface.
     */
    public void moveShipModelToStartPosition() {
        if (spatial == null) {
            System.out.println("WHaaat!?");
        }
        Ship shipNode = (Ship) spatial;
        Spatial shipModel = shipNode.getChild("ship");
        shipModel.move(0, Planet.PLANET_RADIUS + shipNode.getAltitude(), 0);

        shipNode.rotate(FastMath.PI/2, 0, 0);   // Rotates the whole node and therefore
        // also the ship model.
    }
}
