package edu.chalmers.notenoughspace.view;

import com.google.common.eventbus.Subscribe;
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
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.SkyFactory;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import edu.chalmers.notenoughspace.core.Level;
import edu.chalmers.notenoughspace.core.Movement;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.GameOverEvent;
import edu.chalmers.notenoughspace.event.HealthChangedEvent;
import edu.chalmers.notenoughspace.event.StorageChangeEvent;

import javax.annotation.Nonnull;

public class Round extends AbstractAppState implements ScreenController {

    SimpleApplication app;
    Nifty nifty;

    private Level level;

    private Geometry sun;
    private PointLight sunLight;
    private AmbientLight ambientLight;
    private AudioNode happy;
    private ActionListener actionListener;
    private StateManager stateManager;

    public Round(){
        Bus.getInstance().register(this);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        this.stateManager = (StateManager) stateManager;
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
        nifty.gotoScreen("hud");
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
        happy = new AudioNode(app.getAssetManager(), "Sounds/brodyquest.wav", AudioData.DataType.Buffer);
        happy.setLooping(true);  // activate continuous playing
        happy.setPositional(false);
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
        setEnabled(!isEnabled());
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

        for (Movement i : Movement.values()){
            if (app.getInputManager().hasMapping(i.name())){
                app.getInputManager().deleteMapping(i.name());
            }
        }
        app.getInputManager().deleteMapping("toggleBeam");
        app.getInputManager().deleteMapping("pause");
        app.getInputManager().removeListener(actionListener);

        app.getRootNode().detachAllChildren();
        app.getRootNode().updateGeometricState();
        // app.getGuiNode().detachChild(hud);
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if (enabled) {
            //Restore control
            happy.play();
        } else {
            //Remove control
            happy.pause();
        }
        app.getInputManager().setCursorVisible(!enabled);
//        nifty.getCurrentScreen().findElementById("pauseMenu").setVisible(!enabled);
    }

    // Note that update is only called while the state is both attached and enabled.
    @Override
    public void update(float tpf) {
        if (isEnabled()) {
            level.update(tpf);
            hudUpdate(); //TODO Get values from storage
        }
    }

    private void hudUpdate() {
        float timeLeft = level.getTimeLeft();
        Element timerElement = nifty.getCurrentScreen().findElementById("timer");
        timerElement.getRenderer(TextRenderer.class).setText("Time left: " + toTimeFormat(timeLeft));

        float energy = level.getShipsEnergy();
        Element energyElement = nifty.getCurrentScreen().findElementById("energy");
        energyElement.getRenderer(TextRenderer.class).setText("Energy: " + (int) energy);
    }

    //** Eventbased HUD updates */
    @Subscribe
    public void updateStorageHUD(StorageChangeEvent event){
        Element counterElement = nifty.getCurrentScreen().findElementById("cowCount");
        counterElement.getRenderer(TextRenderer.class).setText(event.getNewScore() + " POINTS");

        Element weightElement = nifty.getCurrentScreen().findElementById("weightCount");
        weightElement.getRenderer(TextRenderer.class).setText(event.getNewWeight() + " KG");
    }

    @Subscribe
    public void updateHealthBar(HealthChangedEvent event) {
        Element healthElement = nifty.getCurrentScreen().findElementById("health");
        healthElement.getRenderer(TextRenderer.class).setText("Health: " + event.getNewHealth());
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

    @Subscribe
    public void levelOver(GameOverEvent event){
        stateManager.setState(GameState.STOPPED);
        nifty.gotoScreen("highscore");
    }

    public void quitButtonClicked(){
        stateManager.setState(GameState.STOPPED);
        nifty.gotoScreen("menu");
    }
}