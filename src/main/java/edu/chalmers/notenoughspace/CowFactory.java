package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.Model.Cow;

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

    public Spatial createCow(){
        //Spatial for model. "this"-node located in center of planet still.
        Node cow = new Node();
        Spatial cowModel = assetManager.loadModel("Models/cow.obj");
        cowModel.setLocalTranslation(0, height, 0);
        cowModel.rotate(FastMath.HALF_PI, FastMath.HALF_PI, FastMath.PI);
        cowModel.setMaterial(assetManager.loadMaterial("Materials/CowMaterial.j3m"));
        cow.attachChild(cowModel);
        cow.addControl(new CowControl((ShipNode) player, new Cow()));

        //Debug Cow

//        Geometry x = new Geometry("z",new Arrow(new Vector3f(1,0,0)));
//        Geometry y = new Geometry("z",new Arrow(new Vector3f(0,3,0)));
//        Geometry z = new Geometry("z",new Arrow(new Vector3f(0,0,6)));
//        x.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
//        y.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
//        z.setMaterial(assetManager.loadMaterial("Materials/SunMaterial.j3m"));
//        cow.attachChild(x);
//        cow.attachChild(y);
//        cow.attachChild(z);


        return cow;
    }
}
