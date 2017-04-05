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
 * A model of a hovering space ship able to be navigated around a planet's surface.
 * The Ship object itself is a node (functioning as a pivot node) containing a
 * visual space ship model.
 */
public class Ship extends Node {

    private final String THIRD_PERSON_CAMERA = "followShipCamera";

    /** The distance from the ship to the planet's surface. */
    private final float SHIP_ALTITUDE = 1.8f;

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
        moveShipModelToStartPosition();

        initMovementKeys(inputManager);
        initSpotLight();
        initBeam(assetManager);
        
    }

    /**
     * Creates a visual model of the ship (named "ship") and attaches it
     * to the center of the Ship object (i.e. this node).
     * @param assetManager
     */
    private void createShip3DModel(AssetManager assetManager) {
        Spatial shipModel = new Geometry("ship", createShipMesh()); //Temporary, should be a real model.
        shipModel.setMaterial( createShipMaterial(assetManager) );  //No material needed when real model added.
        shipModel.setName("ship");  //Important, used in many places for accessing the ship model.
        this.attachChild(shipModel);
    }

    //Temporary helper method.
    private Mesh createShipMesh() {
        return new Box(0.2f, 0.1f, 0.2f);
    }
    
    //Temporary helper method.
    private Material createShipMaterial(AssetManager assetManager) {
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);
        return mat;
    }

    /**
     * Moves the ship model from its original position at the center of the Ship node
     * to it's correct starting position over the planet's surface.
     */
    private void moveShipModelToStartPosition() {
        Spatial shipModel = getChild("ship");
        shipModel.move(0, Planet.PLANET_RADIUS + SHIP_ALTITUDE, 0);

        this.rotate(FastMath.PI/2, 0, 0);   // Rotates the whole node and therefore
                                            // also the ship model.
    }



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


    /////////// CAMERA STUFF /////////////
    
    /**
     * Attaches the given camera to a position a bit behind and above
     * the ship, looking at the ship with UP in the ship's direction.
     * @param cam The camera to be used as third person camera.
     */
    public void attachThirdPersonView(Camera cam) {
        CameraNode followShipCamera = new CameraNode(THIRD_PERSON_CAMERA, cam);
        followShipCamera.setLocalTranslation( 0
                , 4f, -(Planet.PLANET_RADIUS + SHIP_ALTITUDE + 3));

        Node followShipCameraPivotNode = new Node();    //Helper node to set the default position
        //of the camera.
        followShipCameraPivotNode.attachChild(followShipCamera);
        followShipCameraPivotNode.rotate(FastMath.HALF_PI + -8*FastMath.DEG_TO_RAD,
                FastMath.PI,0);


        //PRESS C TO GET CAMERA INFO FOR SETTING CHASECAM!
        this.attachChild(followShipCameraPivotNode);



        //use these to change view of the 3rd person camera
        cam.setLocation(new Vector3f(0.09670155f, -0.5602153f, 11.6101885f));
        cam.setRotation(new Quaternion(-4.8353246E-5f, 0.9718176f, 0.23573402f, 1.991157E-4f));
    }

    /**
     * Removes the ship's third person camera (if attached) which restores
     * the camera to the original one.
     */
    public void detachThirdPersonView() {
        if (this.getChild(THIRD_PERSON_CAMERA) != null) {
            CameraNode followShipCamera = (CameraNode) this.getChild(THIRD_PERSON_CAMERA);

            this.detachChild(followShipCamera.getParent());    // Removes the camera
            // getParent() part needed since the CameraNode
            // actually is nested inside a "camera pivot node"
            // which in turn is a child of the shipPivotNode.

            //Restores the original settings of the camera:
            Camera gameCamera = followShipCamera.getCamera();
            gameCamera.setFrame(
                    new Vector3f(0, 0, 10f), // Location
                    new Vector3f(-1f, 0, 0), // Left
                    new Vector3f(0, 1f, 0), // Up
                    new Vector3f(0, 0, -1f)); // Direction
        }
    }

    public boolean hasThirdPersonViewAttached() {
        Node shipPivotNode = this;
        return shipPivotNode.getChild(THIRD_PERSON_CAMERA) != null;
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
