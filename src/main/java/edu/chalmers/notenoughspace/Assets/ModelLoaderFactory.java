package edu.chalmers.notenoughspace.Assets;

/**
 * Created by Phnor on 2017-04-26.
 */
public class ModelLoaderFactory {
    public static IModelLoader getModelLoader(){
        return new ModelManager();
    }
}
