/*package edu.chalmers.notenoughspace.view;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.DefaultScreenController;
import edu.chalmers.notenoughspace.ctrl.StartMenuControl;

/**
 * Created by juliaortheden on 2017-05-15.
 *
public class GUI {

    private SimpleApplication app;
    private Nifty nifty;

    public GUI(SimpleApplication app) {
        this.app = app;


        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(app.getAssetManager(), app.getInputManager(),
                app.getAudioRenderer(), app.getGuiViewPort());

        /** Create a new NiftyGUI object *
        nifty = niftyDisplay.getNifty();

        /** Read your XML and initialize your custom ScreenController *
        nifty.fromXml("Interface/Screens.xml", "menu", new StartMenuControl());

        // attach the Nifty display to the gui view port as a processor
        app.getGuiViewPort().addProcessor(niftyDisplay);
        // disable the fly cam
        app.getFlyByCamera().setDragToRotate(true);
    }

    public void setScreen(String screen) {
        nifty.gotoScreen(screen);
    }
}
*/