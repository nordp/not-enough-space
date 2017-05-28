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
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.move.Movement;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.event.BeamableStoredEvent;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * Control responsible for telling the ship when to update and for making it react
 * on user input.
 */
public class ShipControl extends DetachableControl {

    private final String THIRD_PERSON_CAMERA = "followShipCamera";
    private final float MAX_DISTANCE_TO_CAMERA = 3f;
    private final float CAMERA_DRAG = 5.25f;

    private final boolean usingCameraDrag = true;
    private Camera camera;
    private Node followShipCameraPivotNode;

    private InputManager inputManager;
    private Listener audioListener;

    private final Ship ship;


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

    @Subscribe
    public void playStoreAnimation(BeamableStoredEvent event) {
        float animationSpeed = 5;
        playAnimation("store", animationSpeed);
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
            setupDraggingCameraBoundaries(followShipCamera);
        }
    }


    private void detachThirdPersonView() {
        CameraNode followShipCamera = (CameraNode) ((Node) spatial).getChild(THIRD_PERSON_CAMERA);
        if (followShipCamera != null) {
            Node cameraNode = followShipCamera.getParent();
            ((Node) spatial).detachChild(cameraNode);

            Camera gameCamera = followShipCamera.getCamera();
            restoreDefaultCameraSettings(gameCamera);
        }
    }

    private void restoreDefaultCameraSettings(Camera gameCamera) {
        gameCamera.setFrame(
                new Vector3f(0, 0f, 10f), // Location
                new Vector3f(-1f, 0, 0), // Left
                new Vector3f(0, 1f, 0), // Up
                new Vector3f(0, 0, -1f)); // Direction
    }

    private void toggleThirdPersonCamera(){
        if (hasThirdPersonViewAttached()) {
            detachThirdPersonView();
        } else {
            attachThirdPersonView(camera);
        }
    }

    private boolean hasThirdPersonViewAttached() {
        Node shipPivotNode = (Node) spatial;
        return shipPivotNode.getChild(THIRD_PERSON_CAMERA) != null;
    }

    private void setAudioListenerAtShipPosition() {
        audioListener.setLocation(getModel().getWorldTranslation());
        audioListener.setRotation(getModel().getWorldRotation());
    }

    private void setupDraggingCameraBoundaries(CameraNode followShipCamera) {
        Vector3f shipPos = getModel().getWorldTranslation();
        Vector3f rightVector = getVectorPointingRight(followShipCamera);
        Vector3f forwardVector = getVectorPointingForward(followShipCamera);

        Vector3f localRightBoundary = worldToLocal(shipPos.add(rightVector));
        Vector3f localLeftBoundary = worldToLocal(shipPos.subtract(rightVector));
        Vector3f localForwardBoundary = worldToLocal(shipPos.add(forwardVector));
        Vector3f localBackwardBoundary = worldToLocal(shipPos.subtract(forwardVector));

        Node rightBoundaryPoint = createNode("rightPoint", localRightBoundary);
        Node leftBoundaryPoint = createNode("leftPoint", localLeftBoundary);
        Node forwardBoundaryPoint = createNode("forwardPoint", localForwardBoundary);
        Node backwardBoundaryPoint = createNode("backwardPoint", localBackwardBoundary);


        followShipCameraPivotNode.attachChild(rightBoundaryPoint);
        followShipCameraPivotNode.attachChild(leftBoundaryPoint);
        followShipCameraPivotNode.attachChild(forwardBoundaryPoint);
        followShipCameraPivotNode.attachChild(backwardBoundaryPoint);
    }

    private Vector3f getVectorPointingRight(CameraNode followShipCamera) {
        Vector3f camPos = followShipCamera.getWorldTranslation();
        Vector3f shipPos = getModel().getWorldTranslation();
        Vector3f camToShipVector = shipPos.subtract(camPos);

        Vector3f rightVector = camToShipVector.cross(shipPos);
        rightVector.normalizeLocal();
        return rightVector;
    }

    private Vector3f getVectorPointingForward(CameraNode followShipCamera) {
        Vector3f shipPos = getModel().getWorldTranslation();
        Vector3f rightVector = getVectorPointingRight(followShipCamera);

        Vector3f forwardVector = rightVector.cross(shipPos).mult(-1);
        forwardVector.normalizeLocal();
        return forwardVector;
    }

    private Vector3f worldToLocal(Vector3f worldVector) {
        Vector3f localVector = new Vector3f();
        followShipCameraPivotNode.worldToLocal(worldVector, localVector);
        return localVector;
    }

    private Node createNode(String name, Vector3f position) {
        Node node = new Node();
        node.setName(name);
        node.setLocalTranslation(position);
        return node;
    }


    private void initMovementKeys() {
        inputManager.addMapping(Movement.FORWARD.name(),  new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping(Movement.LEFT.name(),   new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping(Movement.RIGHT.name(),  new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping(Movement.BACKWARD.name(), new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping(Movement.ROTATION_LEFT.name(), new KeyTrigger(KeyInput.KEY_Z));
        inputManager.addMapping(Movement.ROTATION_RIGHT.name(), new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping("toggleBeam", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("cameraMode", new KeyTrigger(KeyInput.KEY_T));

        inputManager.addListener(analogListener,
                Movement.FORWARD.name(),Movement.LEFT.name(),Movement.RIGHT.name(),Movement.BACKWARD.name(),
                Movement.ROTATION_LEFT.name(), Movement.ROTATION_RIGHT.name());
        inputManager.addListener(actionListener,
                "toggleBeam", "cameraMode");
    }

    private void cleanupMovementKeys() {
        for (Movement i : Movement.values()) {
            inputManager.deleteMapping(i.name());
        }
        inputManager.deleteMapping("toggleBeam");
        inputManager.deleteMapping("cameraMode");

        inputManager.removeListener(analogListener);
        inputManager.removeListener(actionListener);
    }

    private final AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            if (name.equals(Movement.FORWARD.name())) {
                ship.addMoveInput(Movement.FORWARD, tpf);

                if (usingCameraDrag && distanceToCameraBoundary("backwardPoint") < MAX_DISTANCE_TO_CAMERA) {
                    float angleToMove = -CAMERA_DRAG * tpf * Math.abs(ship.getCurrentYSpeed());
                    followShipCameraPivotNode.rotate(angleToMove, 0, 0);
                }
            } else if (name.equals(Movement.LEFT.name())) {
                ship.addMoveInput(Movement.LEFT, tpf);

                if (usingCameraDrag && distanceToCameraBoundary("rightPoint") < MAX_DISTANCE_TO_CAMERA) {
                    float angleToMove = CAMERA_DRAG * tpf  * Math.abs(ship.getCurrentXSpeed());
                    followShipCameraPivotNode.rotate(0, 0, angleToMove);
                }
            } else if (name.equals(Movement.RIGHT.name())) {
                ship.addMoveInput(Movement.RIGHT, tpf);

                if (usingCameraDrag && distanceToCameraBoundary("leftPoint") < MAX_DISTANCE_TO_CAMERA) {
                    float angleToMove = -CAMERA_DRAG * tpf  * Math.abs(ship.getCurrentXSpeed());
                    followShipCameraPivotNode.rotate(
                            0, 0, angleToMove);
                }
            } else if (name.equals(Movement.BACKWARD.name())) {
                ship.addMoveInput(Movement.BACKWARD, tpf);

                if (usingCameraDrag && distanceToCameraBoundary("forwardPoint") < MAX_DISTANCE_TO_CAMERA) {
                    float angleToMove = CAMERA_DRAG * tpf * Math.abs(ship.getCurrentYSpeed());
                    followShipCameraPivotNode.rotate(
                            angleToMove, 0, 0);
                }
            } else if (name.equals(Movement.ROTATION_LEFT.name())) {
                ship.addMoveInput(Movement.ROTATION_LEFT, tpf);
            } else if (name.equals(Movement.ROTATION_RIGHT.name())) {
                ship.addMoveInput(Movement.ROTATION_RIGHT, tpf);
            }
        }

    };

    private float distanceToCameraBoundary(String boundaryName) {
        Vector3f shipPos = getModel().getWorldTranslation();
        Vector3f boundaryPos = followShipCameraPivotNode.getChild(boundaryName).getWorldTranslation();

        return shipPos.distance(boundaryPos);
    }

    private final ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean value, float tpf) {
            if (name.equals("toggleBeam")) {
                ship.toggleBeam(value);
            } else if (name.equals("cameraMode") && !value) {
                toggleThirdPersonCamera();
            }
        }

    };

    private void playAnimation(String animationName, float speed) {
        Spatial model = getModel();
        AnimControl control = model.getControl(AnimControl.class);
        AnimChannel channel = control.getChannel(0);
        channel.setAnim(animationName);
        channel.setLoopMode(LoopMode.DontLoop);
        channel.setSpeed(speed);
    }

    private Spatial getModel() {
        return ((Node) spatial).getChild("shipModel");
    }

}
