package edu.chalmers.notenoughspace.view.state;


import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import edu.chalmers.notenoughspace.core.Level;

import javax.annotation.Nonnull;


public class Menu extends AbstractAppState implements ScreenController{

    private SimpleApplication app;
    private Nifty nifty;
    private StateManager stateManager;
    private boolean startUp = true;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        app = (SimpleApplication) application;
        this.stateManager = (StateManager) stateManager;
        app.getInputManager().setCursorVisible(true);
    }

    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.nifty = nifty;
    }

    public void onStartScreen() {
        //NA
    }

    public void onEndScreen() {
        //NA
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled && isInitialized() && startUp) {
            nifty.gotoScreen("menu");
            startUp = false;
        } else if (enabled && isInitialized()){
            nifty.gotoScreen("highscore");
        }
    }

    /** Button/Navigation methods */
    public void startButtonClicked() {
        stateManager.setState(GameState.RUNNING);
    }

    public void quitButtonClicked() { app.stop();}

    public void mainMenuButtonClicked(){
        nifty.gotoScreen("menu");
    }

    public String getHighScoreString() {
        Level level = new Level();
        return level.getHighScoreString();
    }

    public void highScoreButtonClicked(){nifty.gotoScreen("highscore");}

}

