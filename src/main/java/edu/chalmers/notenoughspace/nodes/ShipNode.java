package edu.chalmers.notenoughspace.nodes;

import com.jme3.asset.AssetManager;
import com.jme3.light.SpotLight;
import com.jme3.math.FastMath;
import com.jme3.scene.*;
import edu.chalmers.notenoughspace.core.Ship;

/**
 * A core of space ship able to be navigated around a planet's surface.
 * The ShipNode object itself is a node (functioning as a pivot node) containing a
 * visual space ship core.
 */
class ShipNode extends Node {

    /** The ship's private spot light, lighting up the surface beneath it. */
    private SpotLight spotLight;

    /** The ship's tractor beamNode. */
    private BeamNode beamNode;

    //** The core ship. */
    private Ship ship;

    /**
     * Creates a ship core and attaches it to the pivot
     * node that is this object.
     * NOTE: Does not add the third person view camera. This is done
     * in its own separate method.
     * @param assetManager Used to load the core and texture for the ship.
     */
    public ShipNode(Ship ship, AssetManager assetManager) {
        this.ship = ship;

        createShip3DModel(assetManager);

        //initSpotLight();    //TODO: Move somewhere else as well (to the control?).
    }

    /**
     * Creates a visual core of the ship (named "ship") and attaches it
     * to the center of the ShipNode object (i.e. this node).
     * @param assetManager
     */
    private void createShip3DModel(AssetManager assetManager) {
        //Spatial shipModel = new Geometry("ship", createShipMesh()); //Temporary, should be a real core.
        Spatial shipModel = assetManager.loadModel("Models/ufo.obj");
        //shipModel.setMaterial( createShipMaterial(assetManager) );  //No material needed when real core added.
        shipModel.setMaterial(assetManager.loadMaterial("Materials/UfoMaterial.j3m"));
        shipModel.setName("ship");  //Important, used in many places for accessing the ship core.
//        shipModel.scale(0.02f, 0.02f, 0.02f);
        attachChild(shipModel);
    }

    /**
     * Initializes a spotlight directed downwards from the
     * bottom oa f the ship. NOTE: Since a light source only affects the
     * nodes BELOW itself in the hierarchy it must be attached to the
     * root node of the game in order to light up the planet, cows, etc.
     * Therefore it always has to be updated with the coordinates of
     * the ship (which is currently done in the inputManager when the
     * player moves the ship).
     */


    /** Returns the ship's spotlight. */
    public SpotLight getSpotLight() {
        return this.spotLight;
    }

    /**
     * Initializes the beamNode and attaches it to this node.
     * TODO: This should also be moved, but probably after we move the key input.
     * @param assetManager
     */

    //MOVED
    /*
    public void initBeam(AssetManager assetManager) {
        beamNode = new BeamNode(ship, assetManager);
        beamNode.setLocalTranslation(beamNode.getLocalTranslation().add(this.getChild("ship").getLocalTranslation()));
        beamNode.setName("beamNode");
        this.attachChild(beamNode);
    }*/

    //MOVED
    /*public void activateBeam(boolean value) {
        beamNode.setActive(value);
    }*/

}
