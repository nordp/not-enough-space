package edu.chalmers.notenoughspace;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.render.batch.BatchRenderConfiguration;
import edu.chalmers.notenoughspace.assets.AssetLoaderFactory;
import edu.chalmers.notenoughspace.view.state.Menu;
import edu.chalmers.notenoughspace.view.state.Round;
import edu.chalmers.notenoughspace.view.state.StateManager;

import java.awt.GraphicsEnvironment;
import java.awt.DisplayMode;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * The main class initializing the game.
 */
public class Game extends SimpleApplication {


    public static void main(String[] args) {
        Game app = new Game();

        setOverallDisplayMode(app);

        Logger.getLogger("com.jme3").setLevel(SEVERE);
//        Logger.getLogger("de.lessvoid.nifty").setLevel(SEVERE);

        app.start();
    }


    @Override
    public void simpleInitApp() {

        AssetLoaderFactory.setAssetManager(assetManager);
        Menu menu = new Menu();
        Round round = new Round();

        BatchRenderConfiguration config = new BatchRenderConfiguration();
        config.atlasHeight = 4096;
        config.atlasWidth = 4096;
        config.atlasTolerance = 0.75f;

        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort, config);
        Nifty nifty = niftyDisplay.getNifty();

        nifty.fromXml("Interface/Screens.xml", "menu", menu, round);
        menu.bind(nifty, nifty.getScreen("menu"));
        round.bind(nifty, nifty.getScreen("hud"));

        guiViewPort.addProcessor(niftyDisplay);
        stateManager = new StateManager(this, menu, round);
    }


    private static void setOverallDisplayMode(Game app) {
        app.showSettings = false;
        AppSettings appSettings = new AppSettings(true);
        appSettings.put("Title", "Not Enough Space");

        DisplayMode mode = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDisplayMode();
        appSettings.setResolution(mode.getWidth(), mode.getHeight());
        appSettings.setFrequency(mode.getRefreshRate());

        appSettings.setVSync(true);
        appSettings.setFullscreen(true);

        app.setPauseOnLostFocus(true);
        app.setSettings(appSettings);
    }

}
