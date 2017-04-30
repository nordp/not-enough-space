package edu.chalmers.notenoughspace;

        import com.jme3.app.SimpleApplication;
        import com.jme3.renderer.RenderManager;
        import com.jme3.system.AppSettings;
        import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;

        import java.awt.*;

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

    private void setFullScreen() {


    }

    @Override
    public void simpleInitApp() {
        setFullScreen();
        setGoodSpeed();
        ModelLoaderFactory.setAssetManager(assetManager);
        stateManager.attach(new Round(assetManager, inputManager));


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
    }
}
