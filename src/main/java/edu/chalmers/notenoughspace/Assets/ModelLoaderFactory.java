package edu.chalmers.notenoughspace.Assets;

import com.jme3.asset.AssetManager;

/**
 * Created by Phnor on 2017-04-26.
 */
public class ModelLoaderFactory {

    private static AssetManager assetManager;
    private static ModelManager modelManager;

    private ModelLoaderFactory(){}

    public static void setAssetManager(AssetManager assetManager) {
        ModelLoaderFactory.assetManager = assetManager;
    }

    public static IModelLoader getModelLoader() {
        if (modelManager == null) {
           modelManager= new ModelManager(assetManager);
           return modelManager;
        } else {
            return modelManager;
        }
    }
}
