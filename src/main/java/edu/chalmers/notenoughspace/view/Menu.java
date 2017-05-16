package edu.chalmers.notenoughspace.view;


import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;

import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import javax.annotation.Nonnull;


public class Menu extends AbstractAppState implements ScreenController{

    private SimpleApplication app;
    private Nifty nifty;
    private StateManager stateManager;

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
        nifty.gotoScreen("menu");
    }

    /** Button/Navigation methods */
    public void startButtonClicked(String nextScreen) {
        stateManager.setState(GameState.RUNNING);
    }

    public void quitButtonClicked() {
        app.stop();
    }
}

