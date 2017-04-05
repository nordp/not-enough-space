package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Cow extends Node implements Beamable{

    public static final int MIN_WALKSPEED = 10;
    public static final int MAX_WALKSPEED = 100;

    public Cow(AssetManager assetManager, float height){

    }

    public void setModel(Spatial model){
        detachAllChildren();
        model.rotate(FastMath.PI + FastMath.HALF_PI,0, 0);
        attachChild(model);
    }
}