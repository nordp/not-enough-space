package edu.chalmers.notenoughspace.assets;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;
import edu.chalmers.notenoughspace.core.Planet;


class ModelManager implements IModelLoader {

    private AssetManager assetManager;

    protected ModelManager(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    public Spatial loadModel(String modelId) {      //TODO Make Modelmanager not depend on stringID
        Spatial model;
        if (modelId.equals("cow")) {
            model = assetManager.loadModel("Models/Beata.j3o");
            model.scale(0.10f);
            AnimControl control = model.getControl(AnimControl.class);
            AnimChannel channel = control.createChannel();
            channel.setAnim("my_animation");    //My_animation is the name of the walk animation,
                                                //will try to change that soon!
            channel.setSpeed(2.5f);
        } else if (modelId.equals("satellite")){
            model = assetManager.loadModel("Models/ufo.obj");
            model.setMaterial(assetManager.loadMaterial("Materials/UfoMaterial.j3m"));
        } else if (modelId.equals("junk")){
            model = assetManager.loadModel("Models/house.obj");
            model.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
            model.rotate(FastMath.PI + FastMath.HALF_PI, FastMath.PI, 0);
        } else if (modelId.equals("ship")){
            model = assetManager.loadModel("Models/ufo.obj");
            model.setMaterial(assetManager.loadMaterial("Materials/UfoMaterial.j3m"));
        } else if (modelId.equals("beam")){
            model = assetManager.loadModel("Models/beam.obj");
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", new ColorRGBA(0.9f, 0.9f, 0.3f, 0.6f));
            mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            model.setMaterial(mat);
            model.setQueueBucket(RenderQueue.Bucket.Transparent);
            model.setLocalTranslation(0f, 0.24f, 0f);
        } else if (modelId.equals("planet")){
            Sphere shape = new Sphere(100, 100, Planet.PLANET_RADIUS);
            shape.setTextureMode(Sphere.TextureMode.Projected);
            TangentBinormalGenerator.generate(shape);
            Geometry geom = new Geometry("planet", shape);
            geom.setMesh(shape);
            geom.setMaterial(assetManager.loadMaterial("Materials/PlanetMaterial.j3m"));
            model = geom;
        } else if (modelId.equals("sun")) {
            com.jme3.scene.shape.Sphere sunMesh = new com.jme3.scene.shape.Sphere(100, 100, 10f);
            sunMesh.setTextureMode(com.jme3.scene.shape.Sphere.TextureMode.Projected);
            model = new Geometry("sun", sunMesh);
            model.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
        } else if(modelId.equals("farmer")) {
            model = assetManager.loadModel("Models/farmer.obj");
            model.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
        } else if (modelId.equals("hayfork")) {
            model = assetManager.loadModel("Models/spear.j3o");
            model.scale(0.3f);
        } else {
            throw new IllegalArgumentException("No such model");
        }
        return model;
    }
}
