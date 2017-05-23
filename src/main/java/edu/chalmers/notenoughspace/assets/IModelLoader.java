package edu.chalmers.notenoughspace.assets;

import com.jme3.scene.Spatial;

/**
 * Created by Phnor on 2017-04-26.
 */
public interface IModelLoader {
    Spatial loadModel(String modelID);
}
