package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.*;
import com.jme3.scene.shape.Box;

/**
 * A model of space ship able to be navigated around a planet's surface.
 * The Ship object itself is a node (functioning as a pivot node) containing a
 * visual space ship model.
 */
public class Ship extends Node {

    /** The ship's private spot light, lighting up the surface beneath it. */
    private SpotLight spotLight;

    /** The ship's tractor beam. */
    private Beam beam;

    /**
     * Creates a steerable ship model and attaches it to the pivot
     * node that is this object.
     * NOTE: Does not add the third person view camera. This is done
     * in its own separate method.
     * @param assetManager Used to load the model and texture for the ship.
     * @param inputManager Used to map movement to key presses.
     */
    public Ship(AssetManager assetManager, InputManager inputManager) {
        createShip3DModel(assetManager);

        initMovementKeys(inputManager); //TODO: Move somewhere else... (to an appstate?)
        initSpotLight();    //TODO: Move somewhere else as well (to the control?).
        initBeam(assetManager); //TODO: This should also be moved, but probably after we move the key input.
        
    }

    /**
     * Creates a visual model of the ship (named "ship") and attaches it
     * to the center of the Ship object (i.e. this node).
     * @param assetManager
     */
    private void createShip3DModel(AssetManager assetManager) {
        //Spatial shipModel = new Geometry("ship", createShipMesh()); //Temporary, should be a real model.
        Spatial shipModel = assetManager.loadModel("Models/ufo.obj");
        //shipModel.setMaterial( createShipMaterial(assetManager) );  //No material needed when real model added.
        shipModel.setMaterial(assetManager.loadMaterial("Materials/UfoMaterial.j3m"));
        shipModel.setName("ship");  //Important, used in many places for accessing the ship model.
        shipModel.scale(0.02f, 0.02f, 0.02f);
        attachChild(shipModel);
    }
/*
    //Temporary helper method.
    private Mesh createShipMesh() {
        return new Box(0.2f, 0.1f, 0.2f);
    }
    /*
    //Temporary helper method.
    private Material createShipMaterial(AssetManager assetManager) {
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);
        return mat;
    }*/





    /**
     * Initializes a spotlight directed downwards from the
     * bottom of the ship. NOTE: Since a light source only affects the
     * nodes BELOW itself in the hierarchy it must be attached to the
     * root node of the game in order to light up the planet, cows, etc.
     * Therefore it always has to be updated with the coordinates of
     * the ship (which is currently done in the inputManager when the
     * player moves the ship).
     */
    private void initSpotLight() {
        spotLight = new SpotLight();
        spotLight.setSpotRange(10);
        spotLight.setSpotOuterAngle(45 * FastMath.DEG_TO_RAD);
        spotLight.setSpotInnerAngle(5 * FastMath.DEG_TO_RAD);
        spotLight.setPosition(this.getChild("ship").getWorldTranslation());
        spotLight.setDirection(this.getChild("ship").getWorldTranslation().mult(-1));
        spotLight.setName("shipSpotLight");
    }

    /** Returns the ship's spotlight. */
    public SpotLight getSpotLight() {
        return this.spotLight;
    }

    /**
     * Initializes the beam and attaches it to this node.
     * TODO: Check that this method works with real beam, not yet finished.
     * @param assetManager
     */
    private void initBeam(AssetManager assetManager) {
        beam = new Beam(assetManager);
        beam.setLocalTranslation(beam.getLocalTranslation().add(this.getChild("ship").getLocalTranslation()));
        beam.setName("beam");
        this.attachChild(beam);
    }



    /////////// MOVEMENTS BELOW //////////////

    /**
     * Makes it possible for the ship to move in all directions
     * and rotate left and right.
     * @param inputManager
     */
    private void initMovementKeys(InputManager inputManager) {
        inputManager.addMapping("forwards",  new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("left",   new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("right",  new KeyTrigger(KeyInput.KEY_L));
        inputManager.addMapping("backwards", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("rotateLeft", new KeyTrigger(KeyInput.KEY_V));
        inputManager.addMapping("rotateRight", new KeyTrigger(KeyInput.KEY_B));
        inputManager.addMapping("beam", new KeyTrigger(KeyInput.KEY_SPACE));

        // Add the names to the action listener.
        inputManager.addListener(analogListener,
                "forwards","left","right","backwards",
                "rotateLeft", "rotateRight");
        inputManager.addListener(actionListener, "beam");
    }

    /**
     * The listener controlling user input for moving the ship.
     */
    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("forwards")) {
                getMe().rotate(-1*tpf, 0, 0);
            }
            if (name.equals("left")) {
                getMe().rotate(0, 0, 1*tpf);
            }
            if (name.equals("right")) {
                getMe().rotate(0, 0, -1*tpf);
            }
            if (name.equals("backwards")) {
                getMe().rotate(1*tpf, 0, 0);
            }
            if (name.equals("rotateLeft")) {
                getMe().rotate(0, 2*tpf, 0);
            }
            if (name.equals("rotateRight")) {
                getMe().rotate(0, -2*tpf, 0);
            }

            //Adjust the spotLight so that it always follows the ship.
            if (spotLight != null) {
                spotLight.setPosition(getMe().getChild("ship").getWorldTranslation());
                spotLight.setDirection(getMe().getChild("ship").getWorldTranslation().mult(-1));
            }
        }
    };

    /**
     * The listener controlling user input for activating the beam.
     */
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean value, float tpf) {
            if(name.equals("beam")) {
                beam.setActive(value);
            }
        }
    };

    /**
     * To get access to the ship inside the analog listener.
     * (Don't know how else to do it?)
     *
     * @return The ship object ( = this).
     */
    private Ship getMe() {
        return this;
    }

}
