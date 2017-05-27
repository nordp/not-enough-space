package edu.chalmers.notenoughspace.ctrl;

import com.google.common.eventbus.Subscribe;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.audio.Listener;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.move.Movement;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.event.BeamableStoredEvent;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * TODO
 */
public class ShipControl extends DetachableControl {

    private final String THIRD_PERSON_CAMERA = "followShipCamera";
    private final float MAX_DISTANCE_TO_CAMERA = 3f;

    private boolean usingCameraDrag = true;
    private Camera camera;
    private Node followShipCameraPivotNode;

    private InputManager inputManager;
    private Listener audioListener;

    private Ship ship;


    public ShipControl(InputManager inputManager, Listener audioListener, Ship ship) {
        this.inputManager = inputManager;
        initMovementKeys();

        this.ship = ship;
        this.audioListener = audioListener;

        Bus.getInstance().register(this);
    }


    protected void controlUpdate(float tpf) {
        ship.update(tpf);
        setAudioListenerAtShipPosition();
    }

    public void onDetach(){
        cleanupMovementKeys();
        Bus.getInstance().unregister(this);
    }

    public void attachThirdPersonView(Camera cam) {
        camera = cam;

        float cameraAltitude = -(Planet.PLANET_RADIUS + Ship.ALTITUDE + 8);
        float cameraRotation = FastMath.HALF_PI + -35*FastMath.DEG_TO_RAD;

        CameraNode followShipCamera = new CameraNode(THIRD_PERSON_CAMERA, cam);
        followShipCamera.setLocalTranslation(0, 6f, cameraAltitude);

        followShipCameraPivotNode = new Node();    //Helper node to set the default position
                                                    //of the camera.
        followShipCameraPivotNode.attachChild(followShipCamera);
        followShipCameraPivotNode.rotate(cameraRotation, FastMath.PI, 0);

        ((Node) spatial).attachChild(followShipCameraPivotNode);

        if (usingCameraDrag) {
            setupDraggingCamera(followShipCamera);
        }
    }


    private void setAudioListenerAtShipPosition() {
        audioListener.setLocation(getModel().getWorldTranslation());
        audioListener.setRotation(getModel().getWorldRotation());
    }

    
    private void setupDraggingCamera(CameraNode followShipCamera) {
        //Calculates the boundaries for how far from the cameras focal point the
        //ship can move without the camera following it.
        Vector3f camPos = followShipCamera.getWorldTranslation();
        Vector3f shipPos = getShipModel().getWorldTranslation();
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
    public void detachThirdPersonView() {
        if (((Node) spatial).getChild(THIRD_PERSON_CAMERA) != null) {
            CameraNode followShipCamera = (CameraNode) ((Node) spatial).getChild(THIRD_PERSON_CAMERA);

            ((Node) spatial).detachChild(followShipCamera.getParent());    // Removes the camera
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
    }

    private void toggleThirdPersonCamera(){
        if(hasThirdPersonViewAttached()){
            detachThirdPersonView();
        }else
            attachThirdPersonView(camera);
    }

    public boolean hasThirdPersonViewAttached() {
        Node shipPivotNode = (Node) spatial;
        return shipPivotNode.getChild(THIRD_PERSON_CAMERA) != null;
    }

    /////////// MOVEMENTS BELOW //////////////

    /**
     * Makes it possible for the ship to move in all directions
     * and rotate left and right.
     */
    private void initMovementKeys() {
        inputManager.addMapping(Movement.FORWARD.name(),  new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping(Movement.LEFT.name(),   new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping(Movement.RIGHT.name(),  new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping(Movement.BACKWARD.name(), new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping(Movement.ROTATION_LEFT.name(), new KeyTrigger(KeyInput.KEY_Z));
        inputManager.addMapping(Movement.ROTATION_RIGHT.name(), new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping("toggleBeam", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("cameraMode", new KeyTrigger(KeyInput.KEY_T));

        // Add the names to the action listener.
        inputManager.addListener(analogListener,
                Movement.FORWARD.name(),Movement.LEFT.name(),Movement.RIGHT.name(),Movement.BACKWARD.name(),
                Movement.ROTATION_LEFT.name(), Movement.ROTATION_RIGHT.name());
        inputManager.addListener(actionListener, "toggleBeam", "cameraMode");
    }

    private void cleanupMovementKeys() {
        for (Movement i : Movement.values()){
            inputManager.deleteMapping(i.name());
        }
        inputManager.deleteMapping("toggleBeam");
        inputManager.deleteMapping("cameraMode");

        inputManager.removeListener(analogListener);
        inputManager.removeListener(actionListener);
    }


    /**
     * The listener controlling user input for moving the ship.
     */
    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            float drag = 5.25f;
            JMEInhabitant body = new JMEInhabitant(spatial);

            if (name.equals(Movement.FORWARD.name())) {
                ship.addMoveInput(Movement.FORWARD, tpf);
                if (usingCameraDrag && distanceToCameraBoundary("backwardPoint") < MAX_DISTANCE_TO_CAMERA) {
                    followShipCameraPivotNode.rotate(
                            -drag * tpf * Math.abs(ship.getCurrentYSpeed()), 0, 0);
                }
            }
            if (name.equals(Movement.LEFT.name())) {
                ship.addMoveInput(Movement.LEFT, tpf);
                if (usingCameraDrag && distanceToCameraBoundary("rightPoint") < MAX_DISTANCE_TO_CAMERA) {
                    followShipCameraPivotNode.rotate(
                            0, 0, drag * tpf  * Math.abs(ship.getCurrentXSpeed()));
                }
            }
            if (name.equals(Movement.RIGHT.name())) {
                ship.addMoveInput(Movement.RIGHT, tpf);
                if (usingCameraDrag && distanceToCameraBoundary("leftPoint") < MAX_DISTANCE_TO_CAMERA) {
                    followShipCameraPivotNode.rotate(
                            0, 0, -drag * tpf  * Math.abs(ship.getCurrentXSpeed()));
                }
            }
            if (name.equals(Movement.BACKWARD.name())) {
                ship.addMoveInput(Movement.BACKWARD, tpf);
                if (usingCameraDrag && distanceToCameraBoundary("forwardPoint") < MAX_DISTANCE_TO_CAMERA) {
                    followShipCameraPivotNode.rotate(
                            drag * tpf * Math.abs(ship.getCurrentYSpeed()), 0, 0);
                }
            }
            if (name.equals(Movement.ROTATION_LEFT.name())) {
                ship.addMoveInput(Movement.ROTATION_LEFT, tpf);
            }
            if (name.equals(Movement.ROTATION_RIGHT.name())) {
                ship.addMoveInput(Movement.ROTATION_RIGHT, tpf);
            }

//            Node rootNode = ControlUtil.getRoot(spatial);
//            SpotLight light = rootNode.getWorldLightList().
            //Adjust the spotLight so that it always follows the ship.
//            if (ship.getSpotLight() != null) {
//                ship.getSpotLight().setPosition(ship.getChild("ship").getTranslation());
//                ship.getSpotLight().setDirection(ship.getChild("ship").getTranslation().mult(-1));
//            }


        }
    };

    private float distanceToCameraBoundary(String boundaryName) {
        Vector3f shipPos = getShipModel().getWorldTranslation();
        Vector3f boundaryPos = followShipCameraPivotNode.getChild(boundaryName).getWorldTranslation();

        return shipPos.distance(boundaryPos);
    }

    /**
     * The listener controlling user input for activating the beam.
     */
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean value, float tpf) {
            if(name.equals("toggleBeam")) {
                ship.toggleBeam(value);
            }else if(name.equals("cameraMode") && value == false) {
                toggleThirdPersonCamera();
            }
        }
    };

    /**Helper method for easy access to the ship core.*/
    private Spatial getShipModel() {
        return ((Node) spatial).getChild("shipModel");
    }


    @Subscribe
    public void playStoreAnimation(BeamableStoredEvent event) {
        playAnimation("store", 5);
    }

    private void playAnimation(String animationName, float speed) {
        Spatial model = getShipModel();
        AnimControl control = model.getControl(AnimControl.class);
        AnimChannel channel = control.getChannel(0);
        channel.setAnim(animationName);
        channel.setLoopMode(LoopMode.DontLoop);
        channel.setSpeed(speed);
    }

    private Spatial getModel() {
        return ((Node)spatial).getChild("shipModel");
    }

}
