package edu.chalmers.notenoughspace.assets;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
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
            model = assetManager.loadModel("Models/cow.obj");
            model.setMaterial(assetManager.loadMaterial("Materials/CowMaterial.j3m"));
            model.rotate(FastMath.HALF_PI, FastMath.HALF_PI, FastMath.PI);
        } else if (modelId.equals("satellite")){
            model = assetManager.loadModel("Models/ufo.obj");
            model.setMaterial(assetManager.loadMaterial("Materials/UfoMaterial.j3m"));
        } else if (modelId.equals("house")){
            model = assetManager.loadModel("Models/house.obj");
            model.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
            model.rotate(FastMath.PI + FastMath.HALF_PI, FastMath.PI, 0);
        } else if (modelId.equals("ship")){
            model = assetManager.loadModel("Models/ufo.obj");
            model.setMaterial(assetManager.loadMaterial("Materials/UfoMaterial.j3m"));
        } else if (modelId.equals("planet")){
            Sphere shape = new Sphere(100, 100, Planet.PLANET_RADIUS);
            shape.setTextureMode(Sphere.TextureMode.Projected);
            TangentBinormalGenerator.generate(shape);
            Geometry geom = new Geometry("planet", shape);
            geom.setMesh(shape);
            geom.setMaterial(assetManager.loadMaterial("Materials/PlanetMaterial.j3m"));
            model = geom;
        } else {
            return null;
        }
        return model;
    }
}
