package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.assets.IModelLoader;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.model.Cow;
import edu.chalmers.notenoughspace.ctrl.CowControl;
import edu.chalmers.notenoughspace.nodes.ShipNode;

/**
 * Created by Phnor on 2017-04-04.
 */
public class CowFactory {
    private IModelLoader modelLoader;
    private Node player;
    private float height;

    public CowFactory(AssetManager modelLoader, Node player, float height){
        this.modelLoader = ModelLoaderFactory.getModelLoader();
        this.player = player;
        this.height = height;
    }

    public Spatial createCow(){
        //Spatial for model. "this"-node located in center of planet still.
        Node cow = new Node();
        Spatial cowModel = modelLoader.loadModel("cow");
        cowModel.setLocalTranslation(0, height, 0);
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
