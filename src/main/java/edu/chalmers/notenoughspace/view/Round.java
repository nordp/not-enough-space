package edu.chalmers.notenoughspace.view;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import edu.chalmers.notenoughspace.core.Level;
import edu.chalmers.notenoughspace.core.Planet;
import edu.chalmers.notenoughspace.core.Ship;
import edu.chalmers.notenoughspace.ctrl.SpatialHandler;
import edu.chalmers.notenoughspace.event.Bus;

public class Round extends AbstractAppState {

    SimpleApplication app;

    private Level level;

    private Geometry sun;
    private DirectionalLight sunLight;
    private AmbientLight ambientLight;
    private AudioNode happy;
    private HUDNode hud;
    private ActionListener actionListener;
    private boolean paused;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        //Init level
        level = new Level();
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);
        app = (SimpleApplication) stateManager.getApplication();
        initScene(app);
        initSound(app);
        initInput(app);
        new SpatialHandler(app);

        //Init HUD
        hud = new HUDNode(app.getContext().getSettings().getHeight(), app.getContext().getSettings().getWidth());
        app.getGuiNode().attachChild(hud);
    }

    private void initScene(SimpleApplication app) {

        //Sun:
        Sphere sunMesh = new Sphere(100, 100, 10f);
        sunMesh.setTextureMode(Sphere.TextureMode.Projected);
        sun = new Geometry("sun", sunMesh);
        sun.setMaterial(app.getAssetManager().loadMaterial("Materials/SunMaterial.j3m"));
        sun.move(-20, 0, 10);
        sun.setLocalTranslation(-100, 0, 0);
        sun.rotate(0, 0, FastMath.HALF_PI); //It has an ugly line at the equator,
        // that's why the rotation is currently needed...

        //Sunlight:
        sunLight = new DirectionalLight();
        sunLight.setDirection(new Vector3f(2, 0, -1).normalizeLocal());
        sunLight.setColor(ColorRGBA.White);

        //AmbientLight:
        ambientLight = new AmbientLight(ColorRGBA.White.mult(0.3f));
        ambientLight.setEnabled(true);
        app.getRootNode().attachChild(sun);
        app.getRootNode().addLight(sunLight);
        app.getRootNode().addLight(ambientLight);
    }

    private void initSound(SimpleApplication app) {
        //Happy :)
        happy = new AudioNode(app.getAssetManager(), "Sounds/happy_1.WAV", AudioData.DataType.Buffer);
        happy.setLooping(true);  // activate continuous playing
        happy.setPositional(true);
        happy.setVolume(1);
        happy.play(); // play continuously!
        app.getRootNode().attachChild(happy);
    }

    private void initInput(SimpleApplication app) {
        actionListener = new ActionListener() {

            public void onAction(String name, boolean value, float tpf) {
                if (name.equals("pause") && !value) {
                    pausePressed();
                }

                /*if (name.equals("cameraMode") && !value) {
                    if (ship.getShipControl().hasThirdPersonViewAttached()) {
                        getShipControl().detachThirdPersonView();
                    } else {
                        getShipControl().attachThirdPersonView(
                                app.getCamera(), PLANET_RADIUS, SHIP_ALTITUDE);
                    }
                }*/
            }
        };

        app.getInputManager().addMapping("pause", new KeyTrigger(KeyInput.KEY_P));
        app.getInputManager().addListener(actionListener, "pause");

        //Adds option to change camera view:
        app.getInputManager().addMapping("cameraMode", new KeyTrigger(KeyInput.KEY_T));
        app.getInputManager().addListener(actionListener, "cameraMode");
    }

    private void pausePressed() {
        paused = !paused;
    }

    @Override
    public void cleanup() {
        super.cleanup();

        //getShipControl().detachThirdPersonView();
        //app.getRootNode().detachChild(ship);
        //app.getRootNode().removeLight(ship.getSpotLight());

        //app.getRootNode().detachChild(planet);
        app.getRootNode().detachChild(sun);
        app.getRootNode().removeLight(sunLight);
        app.getRootNode().removeLight(ambientLight);
        app.getRootNode().detachChild(happy);
        happy.stop();   //Why is this needed? (Without it the music keeps playing!)

        app.getInputManager().deleteMapping("pause");
        app.getInputManager().removeListener(actionListener);

        app.getGuiNode().detachChild(hud);
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if (enabled) {
            //Restore control
            paused = false;
            happy.play();
        } else {
            //Remove control
            paused = true;
            happy.pause();
        }
    }

    // Note that update is only called while the state is both attached and enabled.
    @Override
    public void update(float tpf) {
        if (!paused) {
            level.update(tpf);
            hud.updateTimer(level.getTimeLeft());
        }
    }

    private void restartRound() {
        app.getStateManager().detach(this);
        app.getStateManager().attach(new Round());
    }

    private void returnToMenu() {
        app.getStateManager().detach(this);
        app.getStateManager().attach(new Menu());
    }
}
