package edu.chalmers.notenoughspace.view;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import edu.chalmers.notenoughspace.event.GameOverEvent;

import javax.annotation.Nonnull;

/**
 * Created by Phnor on 2017-05-15.
 */
public class StateManager extends AppStateManager{

    private AbstractAppState stopped;
    private AbstractAppState running;
    private AbstractAppState paused;
    private AbstractAppState current;

    public StateManager(SimpleApplication app, AbstractAppState stopped, AbstractAppState running, AbstractAppState paused){
        super(app);
        this.stopped = stopped;
        this.running = running;
        this.paused = paused;
        setState(GameState.STOPPED);
    }

    /** State Managing */
    protected void setState(GameState state) {
        detach(current);
        current = getState(state);
        attach(current);
        current.setEnabled(true); //TODO: Maybe should not enable by default
    }

    /** State Listeners */
    @Subscribe
    public void gameOver(GameOverEvent event){
        setState(GameState.STOPPED);     //TODO: Implement result screen
    }

    /** Helper Methods */
    private AbstractAppState getState(GameState state) {
        switch (state){
            case STOPPED:
                return stopped;
            case RUNNING:
                return running;
            case PAUSED:
                return paused;
            default:
                throw new IllegalArgumentException("No such GameState");
        }
    }
}
