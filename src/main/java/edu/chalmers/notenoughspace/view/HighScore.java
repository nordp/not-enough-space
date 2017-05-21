package edu.chalmers.notenoughspace.view;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import edu.chalmers.notenoughspace.event.GameOverEvent;

import javax.annotation.Nonnull;

/**
 * Created by juliaortheden on 2017-05-18.
 */
public class HighScore extends AbstractAppState implements ScreenController {

    private SimpleApplication app;
    private Nifty nifty;
    private StateManager stateManager;

    //TODO: High score list? When time is up or player is out of HP

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

    }

    public void onEndScreen() {

    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled && isInitialized()) {
            app.getInputManager().setCursorVisible(true);
            nifty.gotoScreen("highscore");
        }
    }

    /** Button/Navigation methods */

    //TODO: implement high score list

    public void playAgainButtonClicked(String nextScreen){ stateManager.setState(GameState.RUNNING);}

    public void quitButtonClicked() { app.stop();}

    public String getPlayerName(){
        return System.getProperty("user.name");
    }

    public int getPlayerScore(){
        return 12;
    }

}
