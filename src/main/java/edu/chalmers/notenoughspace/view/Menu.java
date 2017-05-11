package edu.chalmers.notenoughspace.view;


import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.controls.ActionListener;

import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.texture.Texture;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import edu.chalmers.notenoughspace.assets.*;

import java.security.Key;


public class Menu extends AbstractAppState {
    private ActionListener actionListener;
    private SimpleApplication app;
    private Round round;

    public Menu() {
        actionListener = new ActionListener() {

            public void onAction(String name, boolean value, float tpf) {
                if (name.equals("StartButton")|| name.equals("QuitButton") || name.equals("OptionsButton") && !value) {
                    startRound();

                }
            };
        };
    }
@Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, app);
        app = (SimpleApplication) application;

        round = new Round();
        simpleInitMenu();
        //round.setEnabled(false);

        app.getInputManager().setCursorVisible(true);


        app.getInputManager().addMapping("start", new MouseButtonTrigger(1));
        app.getInputManager().addListener(actionListener, "Start");

        app.getInputManager().addMapping("options", new MouseButtonTrigger(1));
        app.getInputManager().addListener(actionListener, "options");

        app.getInputManager().addMapping("exit", new MouseButtonTrigger(1));
        app.getInputManager().addListener(actionListener, "exit");

    }

    private void startRound() {
        app.getStateManager().detach(this);
        app.getStateManager().attach(round);
        round.setEnabled(true);

    }
/*
    private void initStartRoundText() {
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        startRoundText = new BitmapText(font);
        startRoundText.setSize(30);
        startRoundText.move(app.getContext().getSettings().getWidth()/4,
                app.getContext().getSettings().getHeight()/2,
                0);
        startRoundText.setText("PRESS ENTER TO START A NEW ROUND!");
        app.getGuiNode().attachChild(startRoundText);
    }

    private void initOptionsText() {
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        optionsText = new BitmapText(font);
        optionsText.setSize(30);
        optionsText.move(app.getContext().getSettings().getWidth()/4,
                app.getContext().getSettings().getHeight()/3,
                0);
        optionsText.setText("OPTIONS");
        app.getGuiNode().attachChild(optionsText);
    }

    private void initExitText() {
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        exitRoundText = new BitmapText(font);
        exitRoundText.setSize(30);
        exitRoundText.move(app.getContext().getSettings().getWidth()/4,
                app.getContext().getSettings().getHeight()/5,
                0);
        exitRoundText.setText("EXIT");
        app.getGuiNode().attachChild(exitRoundText);

    }

    @Override
    public void cleanup() {
        super.cleanup();
        app.getGuiNode().detachAllChildren();
        app.getInputManager().deleteMapping("startRound");
        app.getInputManager().deleteMapping("options");
        app.getInputManager().deleteMapping("exit");
        app.getInputManager().removeListener(actionListener);

    }

*/

        public void simpleInitMenu() {
            NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                    app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
            Nifty nifty = niftyDisplay.getNifty();
            app.getGuiViewPort().addProcessor(niftyDisplay);
            app.getFlyByCamera().setDragToRotate(true);

            nifty.loadStyleFile("nifty-default-styles.xml");
            nifty.loadControlFile("nifty-default-controls.xml");

            // add start screen

            nifty.addScreen("start", new ScreenBuilder("start") {
                {
                    controller(new DefaultScreenController());
                    layer(new LayerBuilder("background") {
                        {
                            childLayoutCenter();
                            //backgroundColor("#000f");

                            // add image
                            image(new ImageBuilder() {{
                                filename("Textures/space.jpg");
                                height("100%");
                                width("100%");
                            }});



                            layer(new LayerBuilder("foreground") {
                                {
                                    childLayoutVertical();
                                    // backgroundColor("#0000");
                                }
                            });


                            // panel added
                            panel(new PanelBuilder("panel_top") {
                                {
                                    childLayoutCenter();
                                    //alignCenter();
                                    //backgroundColor("#f008");
                                    height("25%");
                                    width("75%");

                                    // add text
                                    text(new TextBuilder() {{
                                        text("My Cool Game");
                                        font("Interface/Fonts/Default.fnt");
                                        height("100%");
                                        width("100%");
                                    }});

                                }});



                                    panel(new PanelBuilder("panel_mid") {
                                        {
                                            childLayoutCenter();
                                            alignCenter();

                                            //backgroundColor("#0f08");
                                            height("25%");
                                            width("75%");

                                            control(new ButtonBuilder("OptionsButton", "Options") {{
                                                alignCenter();
                                                valignCenter();
                                                height("25%");
                                                width("25%");
                                            }});
                                        }});

                                    panel(new PanelBuilder("panel_bottom") {
                                        {
                                            childLayoutHorizontal();
                                            alignCenter();
                                            //backgroundColor("#00f8");
                                            height("25%");
                                            width("75%");



                                    panel(new PanelBuilder("panel_bottom_left") {{
                                        childLayoutCenter();
                                        valignCenter();
                                        //backgroundColor("#44f8");
                                        height("50%");
                                        width("50%");

                                        // add control
                                        control(new ButtonBuilder("StartButton", "Start") {{
                                            alignCenter();
                                            valignCenter();
                                            height("50%");
                                            width("50%");
                                        }});

                                    }});

                                    panel(new PanelBuilder("panel_bottom_right") {
                                        {
                                            childLayoutCenter();
                                            valignCenter();
                                            // backgroundColor("#88f8");
                                            height("50%");
                                            width("50%");

                                            // add control
                                            control(new ButtonBuilder("QuitButton", "Quit") {{
                                                alignCenter();
                                                valignCenter();
                                                height("50%");
                                                width("50%");
                                            }});
                                        }});
                                     }});
                                }});





      }}.build(nifty));


    nifty.gotoScreen("start"); // start the screen
    }
}
