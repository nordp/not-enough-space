package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.assets.IModelLoader;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.ctrl.SatelliteControl;
import edu.chalmers.notenoughspace.model.Cow;
import edu.chalmers.notenoughspace.ctrl.CowControl;
import edu.chalmers.notenoughspace.model.Planet;
import edu.chalmers.notenoughspace.nodes.JunkNode;
import edu.chalmers.notenoughspace.nodes.SatelliteNode;
import edu.chalmers.notenoughspace.nodes.ShipNode;

/**
 * Created by Phnor on 2017-04-04.
 */
public class NodeFactory {
    private IModelLoader modelLoader;

    public NodeFactory(){
        this.modelLoader = ModelLoaderFactory.getModelLoader();
    }

    public Spatial createCow(){
        //Spatial for model. "this"-node located in center of planet still.
        Node cow = new Node();
        Spatial cowModel = modelLoader.loadModel("cow");
        cowModel.setLocalTranslation(0, Planet.PLANET_RADIUS, 0);
        cow.attachChild(cowModel);
        cow.addControl(new CowControl());

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

    public Spatial createJunk(){
        Node junkNode = new JunkNode();
        Spatial junkModel = modelLoader.loadModel("house");

        junkModel.setLocalTranslation(0, Planet.PLANET_RADIUS, 0);
        junkModel.scale(0.01f, 0.01f, 0.01f);

        junkNode.attachChild(junkModel);
        
        return junkNode;
    }

    public Spatial createSatellite(){
        Node satelliteNode = new SatelliteNode();
        Spatial satelliteModel = modelLoader.loadModel("satellite");

        satelliteModel.setLocalTranslation(0,Planet.PLANET_RADIUS+2,0);
        satelliteModel.scale(0.01f, 0.01f, 0.01f);

        satelliteNode.attachChild(satelliteModel);
        satelliteNode.addControl(new SatelliteControl());

        return satelliteModel;
    }

    //public Spatial createPlanet(){} TODO

    //public Spatial createShip(){} TODO
}
