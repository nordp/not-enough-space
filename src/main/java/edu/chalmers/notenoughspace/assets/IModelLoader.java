package edu.chalmers.notenoughspace.assets;

import com.jme3.scene.Spatial;

/**
 * Interface for model loader.
 */
public interface IModelLoader {

    Spatial loadModel(String modelID);

}
