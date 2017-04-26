package edu.chalmers.notenoughspace.Assets;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Spatial;


class ModelManager implements IModelLoader {

    private AssetManager assetManager;

    public Spatial loadModel(String modelId) {
        Spatial model= null;
        if (modelId.equals("cow")) {
            Spatial cowModel = assetManager.loadModel("Models/cow.obj");
            cowModel.rotate(FastMath.HALF_PI, FastMath.HALF_PI, FastMath.PI);
        } else if (modelId.equals("satellite")){
            Spatial satelliteModel = assetManager.loadModel("Models/ufo.obj");
        } else if (modelId.equals("house")){
            Spatial houseModel = assetManager.loadModel("Models/house.obj");
            houseModel.rotate(FastMath.PI + FastMath.HALF_PI, FastMath.PI, 0);
        } else if (modelId.equals("ship")){
            Spatial shipModel = assetManager.loadModel("Models/ufo.obj");
        } else
            return null;
        return model;
    }
}
