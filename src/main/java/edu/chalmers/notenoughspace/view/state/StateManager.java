package edu.chalmers.notenoughspace.view.state;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * Manager providing the different states in which the game round can be.
 */
public class StateManager extends AppStateManager {

    private AbstractAppState stopped;
    private AbstractAppState running;
    private AbstractAppState current;

    public StateManager(SimpleApplication app, AbstractAppState stopped, AbstractAppState running){
        super(app);

        this.stopped = stopped;
        this.running = running;

        setState(GameState.STOPPED);

        Bus.getInstance().register(this);
    }

    void setState(GameState state) {
        detach(current);

        current = getState(state);
        attach(current);

        current.setEnabled(true); //TODO: Maybe should not enable by default
    }

    /*
    @Subscribe
    public void gameOver(GameOverEvent event){
        setState(GameState.STOPPED);     //TODO: Implement result screen
    }*/

    private AbstractAppState getState(GameState state) {
        switch (state){
            case STOPPED:
                return stopped;
            case RUNNING:
                return running;
            default:
                throw new IllegalArgumentException("No such GameState.");
        }
    }

}
