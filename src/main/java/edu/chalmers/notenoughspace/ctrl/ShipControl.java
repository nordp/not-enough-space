package edu.chalmers.notenoughspace.ctrl;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.Ship;

/**
 * Control for a ship hovering around a planet. Includes functions
 * for adding and removing a third person camera.
 */
public class ShipControl extends AbstractControl {
    private final String THIRD_PERSON_CAMERA = "followShipCamera";
    private final float MAX_DISTANCE_TO_CAMERA = 3f;
    private boolean usingCameraDrag = true;
    private Ship ship;

    /**
     * Node for the camera following the ship.
     */
    private Node followShipCameraPivotNode;

    public ShipControl(InputManager inputManager, Ship ship) {
        initMovementKeys(inputManager);
        this.ship = ship;
    }

    protected void controlUpdate(float v) {

    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }

    /////////// CAMERA STUFF /////////////

    /**
     * Attaches the given camera to a position a bit behind and above
     * the ship, looking at the ship with UP in the ship's direction.
     * @param cam The camera to be used as third person camera.
     * @param planetRadius The radius of the planet that the ship is hovering over.
     * @param shipAltitude The ship's height above the planet's surface.
     */
    public void attachThirdPersonView(Camera cam, float planetRadius, float shipAltitude) {
        CameraNode followShipCamera = new CameraNode(THIRD_PERSON_CAMERA, cam);
        followShipCamera.setLocalTranslation( 0
                ,6f, -(planetRadius + shipAltitude + 8));

        /*followShipCameraPivotNode = new Node();    //Helper node to set the default position
        //of the camera.
        followShipCameraPivotNode.attachChild(followShipCamera);
        followShipCameraPivotNode.rotate(FastMath.HALF_PI + -35*FastMath.DEG_TO_RAD,
                FastMath.PI, 0); //originally 43

        //PRESS C TO GET CAMERA INFO FOR SETTING CHASECAM!
        ((ShipNode) spatial).attachChild(followShipCameraPivotNode);*/

        if (usingCameraDrag) {
            setupDraggingCamera(followShipCamera);
        }

    }

    private void setupDraggingCamera(CameraNode followShipCamera) {
        //Calculates the boundaries for how far from the cameras focal point the
        //ship can move without the camera following it.
        Vector3f camPos = followShipCamera.getWorldTranslation();
        Vector3f shipPos = new Vector3f(ship.getTranslation().x, ship.getTranslation().y, ship.getTranslation().z);
        Vector3f camToShip = shipPos.subtract(camPos);

        Vector3f realRightVector = camToShip.cross(shipPos).normalize().normalizeLocal();
        Vector3f realRightBoundary = shipPos.add(realRightVector);
        Vector3f localRightBoundary = new Vector3f();
        followShipCameraPivotNode.worldToLocal(realRightBoundary, localRightBoundary);

        Vector3f realLeftBoundary = shipPos.subtract(realRightVector);
        Vector3f localLeftBoundary = new Vector3f();
        followShipCameraPivotNode.worldToLocal(realLeftBoundary, localLeftBoundary);

        Vector3f realForwardVector = realLeftBoundary.cross(shipPos).normalize().normalizeLocal();
        Vector3f realForwardBoundary = shipPos.add(realForwardVector);
        Vector3f localForwardBoundary = new Vector3f();
        followShipCameraPivotNode.worldToLocal(realForwardBoundary, localForwardBoundary);

        Vector3f realBackwardBoundary = shipPos.subtract(realForwardVector);
        Vector3f localBackwardBoundary = new Vector3f();
        followShipCameraPivotNode.worldToLocal(realBackwardBoundary, localBackwardBoundary);

        //Actually implements the boundaries mentioned above, as nodes:
        Node rightPoint = new Node();
        rightPoint.setName("rightPoint");
        rightPoint.setLocalTranslation(localRightBoundary);
        followShipCameraPivotNode.attachChild(rightPoint);

        Node leftPoint = new Node();
        leftPoint.setName("leftPoint");
        leftPoint.setLocalTranslation(localLeftBoundary);
        followShipCameraPivotNode.attachChild(leftPoint);

        Node forwardPoint = new Node();
        forwardPoint.setName("forwardPoint");
        forwardPoint.setLocalTranslation(localForwardBoundary);
        followShipCameraPivotNode.attachChild(forwardPoint);

        Node backwardPoint = new Node();
        backwardPoint.setName("backwardPoint");
        backwardPoint.setLocalTranslation(localBackwardBoundary);
        followShipCameraPivotNode.attachChild(backwardPoint);
    }

    /**
     * Removes the ship's third person camera (if attached) which restores
     * the camera to the original one.
     */
    /*public void detachThirdPersonView() {
        ShipNode ship = (ShipNode) spatial;
        if (ship.getChild(THIRD_PERSON_CAMERA) != null) {
            CameraNode followShipCamera = (CameraNode) ship.getChild(THIRD_PERSON_CAMERA);

            ship.detachChild(followShipCamera.getParent());    // Removes the camera
            // getParent() part needed since the CameraNode
            // actually is nested inside a "camera pivot node"
            // which in turn is a child of the shipPivotNode.

            //Restores the original settings of the camera:
            Camera gameCamera = followShipCamera.getCamera();
            gameCamera.setFrame(
                    new Vector3f(0, 0f, 10f), // Location
                    new Vector3f(-1f, 0, 0), // Left
                    new Vector3f(0, 1f, 0), // Up
                    new Vector3f(0, 0, -1f)); // Direction
        }
    }*/

    public boolean hasThirdPersonViewAttached() {
        Node shipPivotNode = (Node) spatial;
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
            float drag = 0.25f;

            if (name.equals("forwards")) {
                spatial.rotate(-1 * tpf, 0, 0);
                /*if (usingCameraDrag && distanceToCameraBoundary("backwardPoint") < MAX_DISTANCE_TO_CAMERA) {
                    followShipCameraPivotNode.rotate(-drag * tpf, 0, 0);
                }*/
            }
            if (name.equals("left")) {
                spatial.rotate(0, 0, 1*tpf);
                /*if (usingCameraDrag && distanceToCameraBoundary("rightPoint") < MAX_DISTANCE_TO_CAMERA) {
                    followShipCameraPivotNode.rotate(0, 0, drag * tpf);
                }*/
            }
            if (name.equals("right")) {
                spatial.rotate(0, 0, -1*tpf);
                /*if (usingCameraDrag && distanceToCameraBoundary("leftPoint") < MAX_DISTANCE_TO_CAMERA) {
                    followShipCameraPivotNode.rotate(0, 0, -drag * tpf);
                }*/
            }
            if (name.equals("backwards")) {
                spatial.rotate(1*tpf, 0, 0);
                /*if (usingCameraDrag && distanceToCameraBoundary("forwardPoint") < MAX_DISTANCE_TO_CAMERA) {
                    followShipCameraPivotNode.rotate(drag * tpf, 0, 0);
                }*/
            }
            if (name.equals("rotateLeft")) {
                spatial.rotate(0, 2*tpf, 0);
            }
            if (name.equals("rotateRight")) {
                spatial.rotate(0, -2*tpf, 0);
            }

            /*ShipNode ship = (ShipNode) spatial;
            //Adjust the spotLight so that it always follows the ship.
            if (ship.getSpotLight() != null) {
                ship.getSpotLight().setPosition(ship.getChild("ship").getTranslation());
                ship.getSpotLight().setDirection(ship.getChild("ship").getTranslation().mult(-1));
            }*/


        }
    };

    /*private float distanceToCameraBoundary(String boundaryName) {
        Vector3f shipPos = ship.getTranslation();
        Vector3f boundaryPos = followShipCameraPivotNode.getChild(boundaryName).getTranslation();

        return shipPos.distance(boundaryPos);
    }*/

    /**
     * The listener controlling user input for activating the beam.
     */
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean value, float tpf) {
            if(name.equals("beam")) {
                ship.setBeamActive(value);
            }
        }
    };

    /**Helper method for easy access to the ship core.*/
    /*private Spatial getShip() {
        return (Spatial) ((ShipNode) spatial).getChild("ship");
    }*/


}
