package edu.chalmers.notenoughspace;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.input.InputManager;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class Round extends AbstractAppState{

    SimpleApplication app;

    private Geometry sun;
    private DirectionalLight sunLight;
    private Planet planet;
    private Ship ship;
    private AudioNode happy;



    public Round(AssetManager assetManager, InputManager inputManager){

        //Ship:
        ship = new Ship(assetManager, inputManager, null);

        //Planet:
        planet = new Planet(assetManager);

        //Sun:
        Sphere sunMesh = new Sphere(100, 100, 10f);
        sunMesh.setTextureMode(Sphere.TextureMode.Projected);
        sun = new Geometry("sun", sunMesh);
        sun.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
        sun.move(-20, 0, 10);
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
    }

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, app);
        app = (SimpleApplication) application;


        ship.attachThirdPersonView(app.getCamera());
        app.getRootNode().attachChild(ship.getShipPivotNode());
        app.getRootNode().attachChild(planet);
        app.getRootNode().attachChild(sun);
        app.getRootNode().addLight(sunLight);
        app.getRootNode().attachChild(happy);


    }

    @Override
    public void cleanup() {
        super.cleanup();

        //detachThirdPersonView
        app.getRootNode().detachChild(ship.getShipPivotNode());
        app.getRootNode().detachChild(planet);
        app.getRootNode().detachChild(sun);
        app.getRootNode().removeLight(sunLight);
        app.getRootNode().detachChild(happy);
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if(enabled){
        } else {
        }
    }

    // Note that update is only called while the state is both attached and enabled.
    @Override
    public void update(float tpf) {
    }

}
