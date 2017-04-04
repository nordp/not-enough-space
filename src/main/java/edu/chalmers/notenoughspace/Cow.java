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
        //Spatial for model. "this"-node located in center of planet still.
        Spatial cow = assetManager.loadModel("Models/cow.obj");
        cow.setMaterial(assetManager.loadMaterial("Materials/CowMaterial.j3m"));

        cow.rotate(FastMath.PI + FastMath.HALF_PI,0, 0);
        cow.setLocalTranslation(0, height, 0);
        attachChild(cow);
    }

    public void setModel(Spatial model){
        detachAllChildren();
        attachChild(model);
    }
}