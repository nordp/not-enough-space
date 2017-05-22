package edu.chalmers.notenoughspace;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.view.*;

import java.awt.GraphicsEnvironment;
import java.awt.DisplayMode;


/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Game extends SimpleApplication {

    public static void main(String[] args) {
        Game app = new Game();

        //Removes the settings window at the start:
        app.showSettings = false; //Look at how to get fullscreen!
        AppSettings appSettings = new AppSettings(true);
        appSettings.put("Title", "Yahoooooo!");

        DisplayMode mode = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDisplayMode();
        appSettings.setResolution(mode.getWidth(), mode.getHeight());
        appSettings.setFrequency(mode.getRefreshRate());

        appSettings.setVSync(true);
        appSettings.setFullscreen(true);

        app.setPauseOnLostFocus(true);
        app.setSettings(appSettings);


        app.start();
    }


    @Override
    public void simpleInitApp() {
        setGoodSpeed();
        ModelLoaderFactory.setAssetManager(assetManager);

        /** Init states */
        Menu menu = new Menu();
        Round round = new Round();
        //Paused paused = new Paused();

        /** Init nifty GUI */
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();

        //TODO Make sure no dead ScreenControllers are initialized
        nifty.fromXml("Interface/Screens.xml", "menu", menu);
        menu.bind(nifty, nifty.getScreen("menu"));
        round.bind(nifty, nifty.getScreen("hud"));
        menu.bind(nifty, nifty.getScreen("highscore"));

        guiViewPort.addProcessor(niftyDisplay);

        stateManager = new StateManager(this, menu, round);


    }


    @Override
    public void simpleUpdate(float tpf) {

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }


    private void setGoodSpeed() {
        this.flyCam.setMoveSpeed(50);
        this.setDisplayFps(false);
        this.setDisplayStatView(false);
        // disable the fly cam TODO Place somewhere else
        flyCam.setDragToRotate(true);
    }
}
