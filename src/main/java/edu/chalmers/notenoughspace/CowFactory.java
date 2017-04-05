package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Created by Phnor on 2017-04-04.
 */
public class CowFactory {
    private AssetManager assetManager;
    private Node player;
    private float height;

    public CowFactory(AssetManager assetManager, Node player, float height){
        this.assetManager = assetManager;
        this.player = player;
        this.height = height;
    }

    public Cow createCow(){
        //Spatial for model. "this"-node located in center of planet still.
        Cow cow = new Cow(assetManager, height);
        Spatial cowModel = assetManager.loadModel("Models/cow.obj");
        cowModel.setLocalTranslation(0, height, 0);
        cowModel.rotate(FastMath.PI + FastMath.HALF_PI, FastMath.PI, 0);
        cowModel.setMaterial(assetManager.loadMaterial("Materials/CowMaterial.j3m"));
        cow.attachChild(cowModel);
        cow.addControl(new CowControl(player));
        return cow;
    }
}
