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
import com.jme3.light.*;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;
import edu.chalmers.notenoughspace.assets.AssetLoaderFactory;
import edu.chalmers.notenoughspace.core.Level;
import edu.chalmers.notenoughspace.event.*;
import edu.chalmers.notenoughspace.view.scene.SpatialHandler;

import javax.annotation.Nonnull;

/**
 * A round of the game. //TODO: Briefly express what round does.
 */
public class Round extends AbstractAppState implements ScreenController {

    private SimpleApplication app;
    private Nifty nifty;
    private Level level;
    private ActionListener actionListener;
    private StateManager stateManager;

    private Spatial sun;
    private DirectionalLight sunLight;
    private AmbientLight ambientLight0;
    private DirectionalLight ambientLight1;
    private DirectionalLight ambientLight2;
    private DirectionalLight ambientLight3;
    private DirectionalLight ambientLight4;
    private DirectionalLight ambientLight5;
    private DirectionalLight ambientLight6;
    private AudioNode music;
    private Element healthBarElement;
    private Element energyBarElement;

    private final SpatialHandler spatialHandler;
    private final Node rootNode;

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

    @Override
    public void cleanup() {
        level.cleanup();

        rootNode.detachChild(sun);
        rootNode.detachChild(music);

        LightList lights = rootNode.getLocalLightList().clone();
        for(Light light : lights){
            rootNode.removeLight(light);
        }

        music.stop();

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
        if (enabled == this.isEnabled()) {
            return;
        }

        super.setEnabled(enabled);

        if (enabled) {
            music.play();
        } else {
            music.pause();
        }

        Element pauseMenu = nifty.getCurrentScreen().findElementById("pauseMenu");
        app.getInputManager().setCursorVisible(!enabled);
        nifty.gotoScreen("hud");
        pauseMenu.setVisible(!enabled);
    }

    //Note that update is only called while the state is both attached and enabled.
    @Override
    public void update(float tpf) {
        level.update(tpf);
        rootNode.updateLogicalState(tpf);
        hudUpdate();
    }

    @Override
    public void render(RenderManager rm) {
        rootNode.updateGeometricState();
    }

    @Override
    public void postRender() {
        if (gameOver) {
            stateManager.setState(GameState.STOPPED);
            nifty.gotoScreen("highscore");
        }
    }

    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.nifty = nifty;
        healthBarElement = nifty.getScreen("hud").findElementById("healthBar");
        energyBarElement = nifty.getScreen("hud").findElementById("energyBar");
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

    public void onStartScreen() {}

    public void onEndScreen() {}

    @Subscribe
    public void levelOver(GameOverEvent event){
        gameOver = true;
    }

    @Subscribe
    public void storageChange(StorageChangedEvent event){
        Element cowCountDisplay = nifty.getCurrentScreen().findElementById("cowCount");

        int numberOfCows = event.getNumberOfCows();
        String count = toTwoDigitsFormat(numberOfCows);
        cowCountDisplay.getRenderer(TextRenderer.class).setText(count);

        Element scoreDisplay = nifty.getCurrentScreen().findElementById("score");
        scoreDisplay.getRenderer(TextRenderer.class).setText(event.getNewScore() + "");
    }

    @Subscribe
    public void healthChanged(HealthChangedEvent event) {
        healthBarElement.setConstraintWidth(new SizeValue(event.getHealthLevel() + "%"));   //Adjusts health meter.
        healthBarElement.getParent().layoutElements();
    }

    @Subscribe
    public void energyChanged(EnergyChangedEvent event) {
        energyBarElement.setConstraintWidth(new SizeValue(event.getEnergyLevel() + "%"));
        energyBarElement.getParent().layoutElements();
    }


    private void initScene() {
        sun = AssetLoaderFactory.getModelLoader().loadModel("sun");
        sun.setLocalTranslation(-100, 0, 0);
        sun.rotate(0, 0, FastMath.HALF_PI);

        Vector3f sunPosition = sun.getWorldTranslation();
        ColorRGBA sunLightColor = ColorRGBA.White.mult(0.5f);
//        int sunLightRadius = 1000;
        sunLight = new DirectionalLight(sunPosition.negate(), sunLightColor);

        ColorRGBA ambientLightColor = ColorRGBA.White.mult(0.2f);
//        ambientLight = new AmbientLight(ambientLightColor);
        ambientLight0 = new AmbientLight(ColorRGBA.White.mult(0.3f));
        ambientLight0.setEnabled(true);
        ambientLight1 = new DirectionalLight(new Vector3f(1f, 0f, 0f), ambientLightColor);
        ambientLight1.setEnabled(true);
        ambientLight2 = new DirectionalLight(new Vector3f(-1f, 0f, 0f), ambientLightColor);
        ambientLight2.setEnabled(true);
        ambientLight3 = new DirectionalLight(new Vector3f(0f, 1f, 0f), ambientLightColor);
        ambientLight3.setEnabled(true);
        ambientLight4 = new DirectionalLight(new Vector3f(0f, -1f, 0f), ambientLightColor);
        ambientLight4.setEnabled(true);
        ambientLight5 = new DirectionalLight(new Vector3f(0f, 0f, 1f), ambientLightColor);
        ambientLight5.setEnabled(true);
        ambientLight6 = new DirectionalLight(new Vector3f(0f, 0f, -1f), ambientLightColor);
        ambientLight6.setEnabled(true);
    }

    private void enableScene(Node rootNode){
        Spatial sky = AssetLoaderFactory.getModelLoader().loadModel("sky");

        rootNode.attachChild(sky);
        rootNode.attachChild(sun);
        rootNode.addLight(sunLight);
        rootNode.addLight(ambientLight0);
        rootNode.addLight(ambientLight1);
        rootNode.addLight(ambientLight2);
        rootNode.addLight(ambientLight3);
        rootNode.addLight(ambientLight4);
        rootNode.addLight(ambientLight5);
        rootNode.addLight(ambientLight6);
    }

    private void initSound() {
        music = AssetLoaderFactory.getSoundLoader().loadSound("brodyquest");

        music.setLooping(true);
        music.setPositional(false);
        music.setVolume(1);
    }

    private void enableSound(Node rootNode){
        rootNode.attachChild(music);
        music.play();
    }

    private void initInput() {
        actionListener = new ActionListener() {

            public void onAction(String name, boolean value, float tpf) {
                if (name.equals("pause") && !value) {
                    pausePressed();
                }
            }
        };
    }

    private void enableInput(SimpleApplication app) {
        app.getInputManager().addMapping("pause", new KeyTrigger(KeyInput.KEY_P));
        app.getInputManager().addListener(actionListener, "pause");
    }

    private void pausePressed() {
        setEnabled(!isEnabled());
    }

    private void hudUpdate() {
        float timeLeft = level.getTimeLeft();
        String[] time = toTimeFormat(timeLeft).split(":");

        Element minutesDisplay = nifty.getCurrentScreen().findElementById("mm");
        minutesDisplay.getRenderer(TextRenderer.class).setText(time[0]);

        Element secondsDisplay = nifty.getCurrentScreen().findElementById("ss");
        secondsDisplay.getRenderer(TextRenderer.class).setText(time[1]);

        Element hundredthsDisplay = nifty.getCurrentScreen().findElementById("hh");
        hundredthsDisplay.getRenderer(TextRenderer.class).setText(time[2]);
    }


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