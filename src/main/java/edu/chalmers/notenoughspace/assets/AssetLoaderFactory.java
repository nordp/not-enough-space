package edu.chalmers.notenoughspace.assets;

import com.jme3.asset.AssetManager;

/**
 * Factory for getting access to the asset loaders.
 */
public class AssetLoaderFactory {

    private static AssetManager assetManager;
    private static IModelLoader modelLoader;
    private static ISoundLoader soundLoader;

    private AssetLoaderFactory(){}  //To prevent instantiation. This class has only static methods.

    public static void setAssetManager(AssetManager assetManager) {
        AssetLoaderFactory.assetManager = assetManager;
    }

    public static IModelLoader getModelLoader() {
        if (modelLoader == null) {
           modelLoader = new ModelLoader(assetManager);
           return modelLoader;
        } else {
            return modelLoader;
        }
    }
    
    public static ISoundLoader getSoundLoader() {
        if (soundLoader == null) {
            soundLoader = new SoundLoader(assetManager);
            return soundLoader;
        } else {
            return soundLoader;
        }
    }

}

