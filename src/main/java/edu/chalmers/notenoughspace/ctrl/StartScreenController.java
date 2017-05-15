package edu.chalmers.notenoughspace.ctrl;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.scene.control.AbstractControl;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import edu.chalmers.notenoughspace.view.Round;

import javax.annotation.Nonnull;

/**
 * Created by juliaortheden on 2017-05-11.
 */
public class StartScreenController extends AbstractAppState implements ScreenController {

    private Nifty nifty;
    private Round round;
    private SimpleApplication application;




    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        app = (SimpleApplication) application;

        round = new Round();
        //round.setEnabled(false);



        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
    }

    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }

    @Override
    public void cleanup() {
        super.cleanup();
        application.getGuiNode().detachAllChildren();
        application.getInputManager().deleteMapping("start");
        application.getInputManager().deleteMapping("options");
        application.getInputManager().deleteMapping("exit");
        //app.getInputManager().removeListener(actionListener);

        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }




    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    public void onStartScreen() {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    public void onEndScreen() {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    /** custom methods */
    public void startGame(String nextScreen) {
        //nifty.gotoScreen(nextScreen);  // switch to another screen
        // start the game and do some more stuff...
        application.getStateManager().detach(this);
        application.getStateManager().attach(round);
        round.setEnabled(true);
    }

    public void quitGame() {
        application.stop();
    }
}
