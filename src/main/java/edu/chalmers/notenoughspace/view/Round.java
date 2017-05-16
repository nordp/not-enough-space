package edu.chalmers.notenoughspace.view;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.SkyFactory;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import edu.chalmers.notenoughspace.core.BeamableEntity;
import edu.chalmers.notenoughspace.core.CountDownTimer;
import edu.chalmers.notenoughspace.core.Level;
import edu.chalmers.notenoughspace.core.Storage;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class Round extends AbstractAppState implements ScreenController {

    SimpleApplication app;
    Nifty nifty;

    private Level level;

    private Geometry sun;
    private PointLight sunLight;
    private AmbientLight ambientLight;
    private AudioNode happy;
    //private HUDNode hud;
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
        sunLight = new PointLight(sun.getWorldTranslation(), ColorRGBA.White, 1000);
//        sunLight.setDirection(new Vector3f(2, 0, -1).normalizeLocal());
//        sunLight.setColor(ColorRGBA.White);

        //AmbientLight:
        ambientLight = new AmbientLight(ColorRGBA.White.mult(0.3f));
        ambientLight.setEnabled(true);
        app.getRootNode().attachChild(SkyFactory.createSky(
                app.getAssetManager(), "Textures/skybox.dds", SkyFactory.EnvMapType.CubeMap));
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

        // app.getGuiNode().detachChild(hud);
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if (enabled) {
            //Restore control
            nifty.gotoScreen("hud");
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
            updateHUD(10,10, level.getTimeLeft()); //TODO Get values from storage

        }
    }

    private void updateHUD(int score, float weight, float timeLeft) {
        Element counterElement = nifty.getCurrentScreen().findElementById("cowCount");
        counterElement.getRenderer(TextRenderer.class).setText(score + " x COW");

        Element weightElement = nifty.getCurrentScreen().findElementById("weightCount");
        weightElement.getRenderer(TextRenderer.class).setText(weight + " KG");

        Element timerElement = nifty.getCurrentScreen().findElementById("timer");
        timerElement.getRenderer(TextRenderer.class).setText("Time left: " + toTimeFormat(timeLeft));
    }

    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.nifty = nifty;
    }

    public void onStartScreen() {

    }

    public void onEndScreen() {

    }

    /** Util methods */

    /**
     * Converts a given number of seconds into standard
     * digital clock format: mm:ss:hh.
     *
     * @param seconds The number of seconds to convert. If negative the time 00:00:00 is returned.
     */
    private static String toTimeFormat(float seconds) {
        if (seconds < 0) {
            seconds = 0;
        }

        int m = (int) seconds / 60;
        int s = (int) seconds % 60;
        int h = (int) ((seconds * 100) % 100);

        String mm = toTwoDigitsFormat(m);
        String ss = toTwoDigitsFormat(s);
        String hh = toTwoDigitsFormat(h);

        return mm + ":" + ss + ":" + hh;
    }

    private static String toTwoDigitsFormat(int number) {
        if (number < 10) {
            return "0" + number;
        }
        return "" + number;
    }
}