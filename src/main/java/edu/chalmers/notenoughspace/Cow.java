package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Cow extends Node implements Beamable{

    public static final int MIN_WALKSPEED = 10;
    public static final int MAX_WALKSPEED = 100;

    public Cow(AssetManager assetManager){
        Spatial cow = assetManager.loadModel("Models/cow.obj");
        cow.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
        setModel(cow);
        setLocalScale(0.01f,0.01f,0.01f);
        move(0, 3.4f, 0);
        rotate(FastMath.PI + FastMath.HALF_PI,0, 0);
    }

    public void setModel(Spatial model){
        detachAllChildren();
        attachChild(model);
    }
}