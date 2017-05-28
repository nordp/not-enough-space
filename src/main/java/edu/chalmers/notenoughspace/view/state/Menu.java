package edu.chalmers.notenoughspace.view.state;


import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import edu.chalmers.notenoughspace.highscore.HighScoreManager;

import javax.annotation.Nonnull;

/**
 * Menu displaying when not in actual game round.
 */
public class Menu extends AbstractAppState implements ScreenController {

    private SimpleApplication app;
    private Nifty nifty;
    private StateManager stateManager;
    private boolean currentlyStartingUp = true;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);

        app = (SimpleApplication) application;
        this.stateManager = (StateManager) stateManager;
        HighScoreManager.initialize(); //Make sure the HighScoreManager singleton is initialized.
        app.getInputManager().setCursorVisible(true);
    }


    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.nifty = nifty;
    }

    public void onStartScreen() {}

    public void onEndScreen() {}

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled && isInitialized() && currentlyStartingUp) {
            nifty.gotoScreen("menu");
            currentlyStartingUp = false;
        } else if (enabled && isInitialized()){
            nifty.gotoScreen("highscore");
        }
    }

    public void startButtonClicked() {
        stateManager.setState(GameState.RUNNING);
    }

    public void quitButtonClicked() { app.stop();}

    public void mainMenuButtonClicked(){
        nifty.gotoScreen("menu");
    }

    public void highScoreButtonClicked(){nifty.gotoScreen("highscore");}

    public String getHighScoreString(){return HighScoreManager.getHighScoreManager().getHighScoreString();}

}


