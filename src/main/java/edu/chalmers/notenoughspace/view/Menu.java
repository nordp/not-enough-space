package edu.chalmers.notenoughspace.view;


import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

public class Menu extends AbstractAppState {
    private ActionListener actionListener;
    private SimpleApplication app;
    private Round round;

    private BitmapText startRoundText;

    public Menu() {
        actionListener = new ActionListener() {

            public void onAction(String name, boolean value, float tpf) {
                if (name.equals("startRound") && !value) {
                    startRound();
                }
            }
        };
    }

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, app);
        app = (SimpleApplication) application;

        round = new Round();
        //round.setEnabled(false);

        app.getInputManager().addMapping("startRound", new KeyTrigger(KeyInput.KEY_RETURN));
        app.getInputManager().addListener(actionListener, "startRound");

        initStartRoundText();
    }

    private void startRound() {
        app.getStateManager().detach(this);
        app.getStateManager().attach(round);
        round.setEnabled(true);
    }

    private void initStartRoundText() {
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        startRoundText = new BitmapText(font);
        startRoundText.setSize(50);
        startRoundText.move(app.getContext().getSettings().getWidth()/4,
                app.getContext().getSettings().getHeight()/2,
                0);
        startRoundText.setText("PRESS ENTER TO START A NEW ROUND!");
        app.getGuiNode().attachChild(startRoundText);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        app.getGuiNode().detachChild(startRoundText);
        app.getInputManager().deleteMapping("startRound");
        app.getInputManager().removeListener(actionListener);
    }
}
