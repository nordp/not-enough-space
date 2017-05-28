package edu.chalmers.notenoughspace.assets;

import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.SkyFactory;
import com.jme3.util.TangentBinormalGenerator;
import edu.chalmers.notenoughspace.core.entity.Planet;

/**
 * Loads or creates 3D models for the game.
 */
class ModelLoader implements IModelLoader {

    private final AssetManager assetManager;

    public ModelLoader(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    public Spatial loadModel(String modelID) {

        Spatial model;

        if (modelID.equals("cow") || modelID.equals("goldenCow")) {
            if(modelID.equals("goldenCow"))
                model = assetManager.loadModel("Models/goldenCow.j3o");
            else
                model = assetManager.loadModel("Models/Beata.j3o");
            model.scale(0.10f);
        } else if (modelID.equals("satellite")){
            model = assetManager.loadModel("Models/satellite.j3o");
            model.scale(0.15f);
            model.rotate(FastMath.DEG_TO_RAD * 25, FastMath.DEG_TO_RAD * 15, FastMath.DEG_TO_RAD * 15);
        } else if (modelID.equals("barn")){
            model = assetManager.loadModel("Models/barn.j3o");
            model.scale(0.26f);
        } else if (modelID.equals("tree")){
            model = assetManager.loadModel("Models/landscape.obj");
            model.setMaterial(assetManager.loadMaterial("Materials/tree.j3m"));
            model.scale(0.3f);
        } else if (modelID.equals("barrel")){
            model = assetManager.loadModel("Models/cupbarrel.obj");
            model.setMaterial(assetManager.loadMaterial("Materials/barrel.j3m"));
            model.scale(0.004f);
            model.rotate(new Quaternion(-0.707f,0,0,0.707f));
        } else if (modelID.equals("ship")){
            model = assetManager.loadModel("Models/redUFO.j3o");
            model.scale(0.36f);
        } else if (modelID.equals("beam")){
            model = assetManager.loadModel("Models/beam_v2.j3o");
            model.setLocalTranslation(0f, 0.24f, 0f);
        } else if (modelID.equals("planet")){
            Sphere planetShape = new Sphere(100, 100, Planet.PLANET_RADIUS);
            planetShape.setTextureMode(Sphere.TextureMode.Projected);
            TangentBinormalGenerator.generate(planetShape);
            Geometry planetModel = new Geometry("planet", planetShape);
            planetModel.setMesh(planetShape);
            planetModel.setMaterial(assetManager.loadMaterial("Materials/PlanetMaterial.j3m"));
            model = planetModel;
        } else if (modelID.equals("sun")) {
            Sphere sunShape = new Sphere(100, 100, 10f);
            sunShape.setTextureMode(com.jme3.scene.shape.Sphere.TextureMode.Projected);
            Geometry sunModel = new Geometry("sun", sunShape);
            sunModel.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
            model = sunModel;
        } else if(modelID.equals("farmer")) {
            model = assetManager.loadModel("Models/Herman.j3o");
            model.scale(0.14f);
            model.rotate(0, -FastMath.HALF_PI, 0);
        } else if (modelID.equals("hayfork")) {
            model = assetManager.loadModel("Models/spear.j3o");
            model.scale(0.3f);
        } else if (modelID.equals("energy")) {
            model = assetManager.loadModel("Models/energy.obj");
            model.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
            model.scale(0.005f);
        } else if (modelID.equals("health")) {
            model = assetManager.loadModel("Models/health.obj");
            Material mat = assetManager.loadMaterial("Materials/SunMaterial.j3m");
            model.setMaterial(mat);
            model.scale(0.005f);
        } else if (modelID.equals("sky")) {
            model = SkyFactory.createSky(
                    assetManager, "Textures/skybox.dds", SkyFactory.EnvMapType.CubeMap);
        } else {
            throw new IllegalArgumentException("No such model listed in loader.");
        }

        if (modelID.equals("cow") || modelID.equals("goldenCow") ||
                modelID.equals("ship") || modelID.equals("farmer")) {
            setUpAnimationChannel(model);
        }

        return model;
    }

    private void setUpAnimationChannel(Spatial model) {
        AnimControl control = model.getControl(AnimControl.class);
        control.createChannel();
    }
}
