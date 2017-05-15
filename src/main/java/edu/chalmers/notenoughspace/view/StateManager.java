package edu.chalmers.notenoughspace.view;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import edu.chalmers.notenoughspace.event.GameOverEvent;

import javax.annotation.Nonnull;

/**
 * Created by Phnor on 2017-05-15.
 */
public class StateManager extends AppStateManager implements ScreenController{

    private AbstractAppState menu;
    private AbstractAppState round;
    private AbstractAppState current;
    private Nifty nifty;

    public StateManager(SimpleApplication app){
        super(app);
        this.menu = new Menu();
        this.round = new Round();
        setState(menu);

        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(app.getAssetManager(), app.getInputManager(),
                app.getAudioRenderer(), app.getGuiViewPort());
        /** Create a new NiftyGUI object */
        nifty = niftyDisplay.getNifty();
        /** Read your XML and initialize your custom ScreenController */
        nifty.fromXml("Interface/Screens.xml", "menu", this);
        // attach the Nifty display to the gui view port as a processor
        app.getGuiViewPort().addProcessor(niftyDisplay);
        // disable the fly cam
        app.getFlyByCamera().setDragToRotate(true);
    }

    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.nifty = nifty;
    }

    public void onStartScreen() {

    }

    public void onEndScreen() {

    }

    /** State Managing */
    private void setState(AbstractAppState state) {
        detach(current);
        current = state;
        attach(current);
        current.setEnabled(true); //TODO: Maybe should not enable by default
    }

    public void setEnabled(boolean enabled){
        current.setEnabled(enabled);
    }

    /** State Listeners */
    @Subscribe
    public void gameOver(GameOverEvent event){
        setState(menu);     //TODO: Implement result screen
    }


    /** Button/Navigation methods */
    public void startButtonClicked(String nextScreen) {
        setState(round);
        nifty.gotoScreen("hud");  // switch to another screen
        // start the game and do some more stuff...
        setEnabled(true);
    }

    public void quitButtonClicked() {
        getApplication().stop();
    }
}
