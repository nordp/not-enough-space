package edu.chalmers.notenoughspace;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class Round extends AbstractAppState{

    /** The distance from the ship to the planet's surface. */
    private final float SHIP_ALTITUDE = 1.8f;
    
    SimpleApplication app;

    private Ship ship;
    private Planet planet;
    private Geometry sun;
    private DirectionalLight sunLight;
    private AudioNode happy;

    private ActionListener actionListener;

    public Round(AssetManager assetManager, InputManager inputManager){

        //Ship:
        ship = new Ship(assetManager, inputManager);
        ShipOverPlanetControl shipControl = new ShipOverPlanetControl();
        ship.addControl(shipControl);
        shipControl.moveShipModelToStartPosition(planet.PLANET_RADIUS, SHIP_ALTITUDE);
        ship.initBeam(assetManager);

        //Planet:
        planet = new Planet(assetManager, ship);

        //Sun:
        Sphere sunMesh = new Sphere(100, 100, 10f);
        sunMesh.setTextureMode(Sphere.TextureMode.Projected);
        sun = new Geometry("sun", sunMesh);
        sun.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
        sun.move(-20, 0, 10);
        sun.setLocalTranslation(100, 0, 0);
        sun.rotate(0, 0, FastMath.HALF_PI); //It has an ugly line at the equator,
                                            // that's why the rotation is currently needed...

        //Sunlight:
        sunLight = new DirectionalLight();
        sunLight.setDirection(new Vector3f(2,0,-1).normalizeLocal());
        sunLight.setColor(ColorRGBA.White);

        //Happy :)
        happy = new AudioNode(assetManager, "Sounds/happy_1.WAV", AudioData.DataType.Buffer);
        happy.setLooping(true);  // activate continuous playing
        happy.setPositional(true);
        happy.setVolume(1);
        happy.play(); // play continuously!

        actionListener = new ActionListener() {

            public void onAction(String name, boolean value, float tpf) {
                Round round = getMe();
                if (name.equals("pause") && !value) {
                    if(round.isEnabled())
                        round.setEnabled(false);
                    else
                        round.setEnabled(true);
                }
                if (name.equals("cameraMode") && !value) {
                    if (getShipControl().hasThirdPersonViewAttached()) {
                        getShipControl().detachThirdPersonView();
                    } else {
                        getShipControl().attachThirdPersonView(
                                app.getCamera(), planet.PLANET_RADIUS, SHIP_ALTITUDE);
                    }
                }
            }
        };
    }

    private Round getMe(){
        return this;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, app);
        app = (SimpleApplication) application;

        getShipControl().attachThirdPersonView(app.getCamera(), planet.PLANET_RADIUS, SHIP_ALTITUDE);
        app.getRootNode().attachChild(ship);
        app.getRootNode().addLight(ship.getSpotLight());

        app.getRootNode().attachChild(planet);
        //Test population
        planet.populate(10,0);

        app.getRootNode().attachChild(sun);
        app.getRootNode().addLight(sunLight);
        app.getRootNode().attachChild(happy);

        app.getInputManager().addMapping("pause",  new KeyTrigger(KeyInput.KEY_P));
        app.getInputManager().addListener(actionListener, "pause");

        //Adds option to change camera view:
        app.getInputManager().addMapping("cameraMode",  new KeyTrigger(KeyInput.KEY_T));
        app.getInputManager().addListener(actionListener, "cameraMode");
    }

    @Override
    public void cleanup() {
        super.cleanup();

        getShipControl().detachThirdPersonView();
        app.getRootNode().detachChild(ship);
        app.getRootNode().removeLight(ship.getSpotLight());

        app.getRootNode().detachChild(planet);
        app.getRootNode().detachChild(sun);
        app.getRootNode().removeLight(sunLight);
        app.getRootNode().detachChild(happy);

        app.getInputManager().deleteMapping("pause");
        app.getInputManager().removeListener(actionListener);
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if(enabled){
            //Restore control
            happy.play();
        } else {
            //Remove control
            happy.pause();
        }
    }

    // Note that update is only called while the state is both attached and enabled.
    @Override
    public void update(float tpf) {
        //Update cow controls? Tick time?
    }

    //Helper method for getting the ship control.
    private ShipOverPlanetControl getShipControl() {
        return (ShipOverPlanetControl) ship.getControl(ShipOverPlanetControl.class);
    }

}
