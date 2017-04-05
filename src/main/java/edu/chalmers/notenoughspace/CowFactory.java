package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
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

        //Spatial for model. "this"-node located in center of planet still.
        this.height = height;
    }

    public Cow createCow(){
        Cow cow = new Cow(assetManager, height);
        cow.setLocalTranslation(0, height, 0);
        Spatial cowModel = assetManager.loadModel("Models/cow.obj");
        cow.setModel(cowModel);
        cowModel.setMaterial(assetManager.loadMaterial("Materials/CowMaterial.j3m"));

        cow.addControl(new CowControl(player));
        return cow;
    }
}
