package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.shape.Box;

public class Ship extends Geometry {

    private SpotLight spot;

    public Ship(AssetManager assetManager, InputManager inputManager,
                Node rootNode) {
        createShipModel(assetManager);
        attachModelToPivotNode();
        initMovementKeys(inputManager);
        initSpotLight(rootNode);
    }

    private void initSpotLight(Node rootNode) {
        spot = new SpotLight();
        spot.setSpotRange(100);
        spot.setSpotOuterAngle(45 * FastMath.DEG_TO_RAD);
        spot.setSpotInnerAngle(5 * FastMath.DEG_TO_RAD);
        spot.setPosition(this.getWorldTranslation());
        spot.setDirection(this.getWorldTranslation().mult(-1));

        rootNode.addLight(spot);
    }

    public void attachThirdPersonView(Camera cam) {
        //Camera:
        Node node2 = new Node();
        CameraNode camNode = new CameraNode("CamNode", cam);
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(0, 0, -8f);
        node2.attachChild(camNode);
        node2.rotate(FastMath.HALF_PI + -8*FastMath.DEG_TO_RAD,
                FastMath.PI,
                0);
        //PRESS C TO GET CAMERA INFO FOR SETTING CHASECAM!
        this.getParent().attachChild(node2);
    }

    private void attachModelToPivotNode() {
        Node thisPivot = new Node("pivot");
        thisPivot.attachChild(this);
        thisPivot.rotate(FastMath.PI/2, 0, 0);
        //thisPivot.attachChild(node2);     //Adds the FPV.

    }

    private void createShipModel(AssetManager assetManager) {
        Box b = new Box(0.2f, 0.1f, 0.2f);
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);

        setMesh(b);
        setMaterial(mat);
        setName("ship");
        move(0, 5f, 0);

    }


    public Node getShipNode(Camera cam) {




        return this.getParent();
    }

    public SpotLight getSpot() {
        return spot;
    }

    private void initMovementKeys(InputManager inputManager) {
        // You can map one or several inputs to one named action
        inputManager.addMapping("forwards",  new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("left",   new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("right",  new KeyTrigger(KeyInput.KEY_L));
        inputManager.addMapping("backwards", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("rotateLeft", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("rotateRight", new KeyTrigger(KeyInput.KEY_V));
        inputManager.addMapping("beam", new KeyTrigger(KeyInput.KEY_SPACE));

        // Add the names to the action listener.
        inputManager.addListener(analogListener,
                "forwards","left","right","backwards",
                "rotateLeft", "rotateRight", "beam");

    }

    private Ship getMe() {
        return this;
    }

    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            Node pivot = (Node) getMe().getParent();
            if (name.equals("forwards")) {
                pivot.rotate(-1*tpf, 0, 0);
            }
            if (name.equals("left")) {
                pivot.rotate(0, 0, 1*tpf);
            }
            if (name.equals("right")) {
                pivot.rotate(0, 0, -1*tpf);
            }
            if (name.equals("backwards")) {
                pivot.rotate(1*tpf, 0, 0);
            }
            if (name.equals("rotateLeft")) {
                pivot.rotate(0, 2*tpf, 0);
            }
            if (name.equals("rotateRight")) {
                pivot.rotate(0, -2*tpf, 0);
            }

            if (spot != null) {
                spot.setPosition(getMe().getWorldTranslation());
                spot.setDirection(getMe().getWorldTranslation().mult(-1));
            }
        }
    };

}
