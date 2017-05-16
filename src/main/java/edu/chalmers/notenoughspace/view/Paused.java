package edu.chalmers.notenoughspace.view;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import javax.annotation.Nonnull;

/**
 * Created by Phnor on 2017-05-16.
 */
public class Paused extends AbstractAppState implements ScreenController{

    SimpleApplication app;
    StateManager stateManager;
    Nifty nifty;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.stateManager = (StateManager) stateManager;
        this.app = (SimpleApplication) app;
    }

    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.nifty = nifty;
    }

    public void onStartScreen() {

    }

    public void onEndScreen() {

    }

    /** Navigation Methods */
    private void restartRound() {
        //TODO
    }

    private void returnToMenu() {
        stateManager.setState(GameState.STOPPED);
    }
}
