package edu.chalmers.notenoughspace.view.state;


import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import edu.chalmers.notenoughspace.highscore.HighScoreManager;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

/**
 * Menu displaying when not in actual game round.
 */
public class Menu extends AbstractAppState implements ScreenController {

    private SimpleApplication app;
    private Nifty nifty;
    private StateManager stateManager;
    private boolean currentlyStartingUp = true;

    public Menu(){
        HighScoreManager.initialize(); //Make sure the HighScoreManager singleton is initialized.
    }

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        app.getInputManager().setCursorVisible(true);

        if (currentlyStartingUp) {
            app.enqueue(new Callable(){
                public Object call() throws Exception {
                    nifty.gotoScreen("menu");
                    return null;
                }
            });
            currentlyStartingUp = false;
        } else {
            nifty.getScreen("highscore").findElementById("highScoreList").getRenderer(TextRenderer.class).setText(getHighScoreString());
            app.enqueue(new Callable(){
                public Object call() throws Exception {
                    nifty.gotoScreen("highscore");
                    return null;
                }
            });
        }
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);

        app = (SimpleApplication) stateManager.getApplication();
        this.stateManager = (StateManager) stateManager;
    }


    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.nifty = nifty;
    }

    public void onStartScreen() {}

    public void onEndScreen() {}

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

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


