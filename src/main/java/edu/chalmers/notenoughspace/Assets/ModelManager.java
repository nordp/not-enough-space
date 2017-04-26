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
        }
        return model;
    }
}
