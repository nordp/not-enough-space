package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Created by juliaortheden on 2017-04-05.
 */
public class Junk extends Node {

    private float height;
    private AssetManager assetManager;

    public Junk(AssetManager assetManager, float height){
        this.height = height;
        this.assetManager = assetManager;
    }


    public Spatial createHouseModel(){
        Spatial house = assetManager.loadModel("Models/house.obj");
        house.setLocalTranslation(0, height, 0);
        house.rotate(FastMath.PI + FastMath.HALF_PI, FastMath.PI, 0);
        house.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
        house.scale(0.01f, 0.01f, 0.01f);
        attachChild(house);
        return house;
    }
}
