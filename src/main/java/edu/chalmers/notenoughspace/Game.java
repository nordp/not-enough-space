package edu.chalmers.notenoughspace;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

public class Game extends SimpleApplication {
    Box b = new Box(1, 1, 1);
    Sphere s = new Sphere(32, 32, 2);
    Geometry geom = new Geometry("Earth", s);

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    @Override
    public void simpleInitApp() {


        s.setTextureMode(Sphere.TextureMode.Projected);
        TangentBinormalGenerator.generate(s);
        Material earthMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        earthMat.setTexture("DiffuseMap", assetManager.loadTexture("Textures/planet.jpg"));
        geom.setMaterial(earthMat);

        rootNode.attachChild(geom);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        super.flyCam.setMoveSpeed(25);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //geom.move(new Vector3f(0.0005f, 0.0005f, 0.0005f));
        geom.rotate(new Quaternion().fromAngleAxis(FastMath.PI/200000,   new Vector3f(0,1,0)));
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
