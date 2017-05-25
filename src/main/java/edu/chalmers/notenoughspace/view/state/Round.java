package edu.chalmers.notenoughspace.view.state;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioNode;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.Light;
import com.jme3.light.LightList;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.core.Level;
import edu.chalmers.notenoughspace.event.*;
import edu.chalmers.notenoughspace.view.scene.SpatialHandler;

import javax.annotation.Nonnull;

public class Round extends AbstractAppState implements ScreenController {

    private SimpleApplication app;
    private Nifty nifty;

    private Level level;
    private SpatialHandler spatialHandler;

    private Spatial sun;
    private PointLight sunLight;
    private AmbientLight ambientLight;
    private AudioNode happy;
    private ActionListener actionListener;
    private StateManager stateManager;
    private Element healthBarElement;
    private Element energyBarElement;

    private Node rootNode;

    private boolean gameOver;

    public Round(){
        Bus.getInstance().register(this);
        spatialHandler = new SpatialHandler();
        rootNode = new Node();
        initScene();
        initSound();
        initInput();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        this.stateManager = (StateManager) stateManager;
        //Init level
        level = new Level();
        gameOver = false;

        enableScene(rootNode);
        enableSound(rootNode);
        enableInput(app);
        app.getViewPort().attachScene(rootNode);
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);
        app = (SimpleApplication) stateManager.getApplication();
        spatialHandler.setApp((SimpleApplication) stateManager.getApplication());
        spatialHandler.setRootNode(rootNode);
        nifty.gotoScreen("hud");
    }

    private void initScene() {

        //Sun:
        sun = ModelLoaderFactory.getModelLoader().loadModel("sun");
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
    }

    private void enableScene(Node rootNode){
        rootNode.attachChild(ModelLoaderFactory.getModelLoader().loadModel("sky"));
        rootNode.attachChild(sun);
        rootNode.addLight(sunLight);
        rootNode.addLight(ambientLight);
    }

    private void initSound() {
        //Happy :)
        happy = ModelLoaderFactory.getSoundLoader().loadSound("brodyquest");
        happy.setLooping(true);  // activate continuous playing
        happy.setPositional(false);
        happy.setVolume(1);
    }

    private void enableSound(Node rootNode){
        rootNode.attachChild(happy);
        happy.play(); // play continuously!
    }

    private void initInput() {
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
    }

    private void enableInput(SimpleApplication app){
        app.getInputManager().addMapping("pause", new KeyTrigger(KeyInput.KEY_P));
//        //Adds option to change camera view:
//        app.getInputManager().addMapping("cameraMode", new KeyTrigger(KeyInput.KEY_T)); Moved to shipcontrol

        app.getInputManager().addListener(actionListener, "pause");
    }

    private void pausePressed() {
        setEnabled(!isEnabled());
    }

    @Override
    public void cleanup() {

        level.cleanup();

        rootNode.detachChild(sun);
        rootNode.detachChild(happy);

        LightList ll = rootNode.getLocalLightList().clone();
        for(Light l : ll){
            rootNode.removeLight(l);
        }

        happy.stop();   //Why is this needed? (Without it the music keeps playing!)

        app.getInputManager().deleteMapping("pause");
        app.getInputManager().removeListener(actionListener);

        rootNode.detachAllChildren();
        rootNode.updateGeometricState();

        app.getViewPort().detachScene(rootNode);

        System.out.println("Scene cleaned up! Current children: " + rootNode.getChildren().size() +
                " Current lights: " + rootNode.getLocalLightList().size());
    }

    @Override
    public void setEnabled(boolean enabled) {
        if(enabled == this.isEnabled())
            return;
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
        nifty.gotoScreen("hud");
        nifty.getCurrentScreen().findElementById("pauseMenu").setVisible(!enabled);
    }

    // Note that update is only called while the state is both attached and enabled.
    @Override
    public void update(float tpf) {
        level.update(tpf);
        rootNode.updateLogicalState(tpf);
        hudUpdate(); //TODO Get values from storage
    }

    @Override
    public void render(RenderManager rm) {
        rootNode.updateGeometricState();
    }

    @Override
    public void postRender() {
        if(gameOver){
            stateManager.setState(GameState.STOPPED);
            nifty.gotoScreen("highscore");
        }
    }

    private void hudUpdate() {
        float timeLeft = level.getTimeLeft();
        String[] time = toTimeFormat(timeLeft).split(":");
        Element mmElement = nifty.getCurrentScreen().findElementById("mm");
        mmElement.getRenderer(TextRenderer.class).setText(time[0]);

        Element ssElement = nifty.getCurrentScreen().findElementById("ss");
        ssElement.getRenderer(TextRenderer.class).setText(time[1]);

        Element hhElement = nifty.getCurrentScreen().findElementById("hh");
        hhElement.getRenderer(TextRenderer.class).setText(time[2]);

//        float energy = level.getShipsEnergy();
//        Element energyElement = nifty.getCurrentScreen().findElementById("energy");
//        energyElement.getRenderer(TextRenderer.class).setText("Energy: " + (int) energy);

//        final int MIN_WIDTH = 32;
//        int pixelWidth = (int) (MIN_WIDTH + (healthBarElement.getParent().getWidth() - MIN_WIDTH) * newHealth);

//        energyBarElement.setConstraintWidth(new SizeValue(energy + "%"));
        
//        energyBarElement.getParent().layoutElements();
    }

    //** Eventbased HUD updates */
    @Subscribe
    public void storageChange(StorageChangeEvent event){
        Element counterElement = nifty.getCurrentScreen().findElementById("cowCount");
        int nCows = event.getNewScore();
        String count = (nCows > 9) ? "" + nCows : "0" + nCows;
        counterElement.getRenderer(TextRenderer.class).setText(count);

        Element pointsElement = nifty.getCurrentScreen().findElementById("score");
        pointsElement.getRenderer(TextRenderer.class).setText(event.getNewWeight() + "");
    }

    @Subscribe
    public void healthChanged(HealthChangedEvent event) {
//        Element healthElement = nifty.getCurrentScreen().findElementById("health");
//        healthElement.getRenderer(TextRenderer.class).setText("Health: " + event.getHealthLevel());

//        final int MIN_WIDTH = 32;
//        int pixelWidth = (int) (MIN_WIDTH + (healthBarElement.getParent().getWidth() - MIN_WIDTH) * newHealth);
        if (event.getHealthLevel() > 8) {
            healthBarElement.setConstraintWidth(new SizeValue(event.getHealthLevel() + "%"));
        } else {
            healthBarElement.setConstraintWidth(new SizeValue("8%"));
        }
        healthBarElement.getParent().layoutElements();
    }

    @Subscribe
    public void energyChanged(EnergyChangedEvent event) {
        energyBarElement.setConstraintWidth(new SizeValue(event.getEnergyLevel() + "%"));
        energyBarElement.getParent().layoutElements();
    }

    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.nifty = nifty;
        healthBarElement = nifty.getScreen("hud").findElementById("healthBar");
        energyBarElement = nifty.getScreen("hud").findElementById("energyBar");
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
        gameOver = true;
    }

    public void quitButtonClicked(){
        stateManager.setState(GameState.STOPPED);
    }

    public void restartButtonClicked() {
        stateManager.setState(GameState.RUNNING);
    }

    public void resumeButtonClicked() {
        setEnabled(true);
    }
}