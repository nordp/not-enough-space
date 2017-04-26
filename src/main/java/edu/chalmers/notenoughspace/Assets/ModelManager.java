package edu.chalmers.notenoughspace.Assets;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Spatial;


class ModelManager implements IModelLoader {

    private AssetManager assetManager;

    protected ModelManager(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    public Spatial loadModel(String modelId) {
        Spatial model = null;
        if (modelId.equals("cow")) {
            model = assetManager.loadModel("Models/cow.obj");
            model.setMaterial(assetManager.loadMaterial("Materials/CowMaterial.j3m"));
            model.rotate(FastMath.HALF_PI, FastMath.HALF_PI, FastMath.PI);
        } else if (modelId.equals("satellite")){
            model = assetManager.loadModel("Models/ufo.obj");
        } else if (modelId.equals("house")){
            Spatial houseModel = assetManager.loadModel("Models/house.obj");
            model.rotate(FastMath.PI + FastMath.HALF_PI, FastMath.PI, 0);
        } else if (modelId.equals("ship")){
            model = assetManager.loadModel("Models/ufo.obj");
        } else {
            return null;
        }
        return model;
    }
}
