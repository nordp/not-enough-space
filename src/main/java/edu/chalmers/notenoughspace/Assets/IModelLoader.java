package edu.chalmers.notenoughspace.Assets;

import com.jme3.scene.Spatial;

/**
 * Created by Phnor on 2017-04-26.
 */
public interface IModelLoader {
    public Spatial loadModel(String modelID);
}
