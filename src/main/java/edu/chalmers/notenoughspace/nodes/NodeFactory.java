package edu.chalmers.notenoughspace.nodes;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.assets.IModelLoader;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.ctrl.SatelliteControl;
import edu.chalmers.notenoughspace.ctrl.CowControl;
import edu.chalmers.notenoughspace.core.Planet;

/**
 * Created by Phnor on 2017-04-04.
 */
public class NodeFactory {
    private IModelLoader modelLoader;

    public NodeFactory(){
        this.modelLoader = ModelLoaderFactory.getModelLoader();
    }

    public Spatial createCow(){
        //Spatial for core. "this"-node located in center of planet still.
        Node cow = new Node();
        Spatial cowModel = modelLoader.loadModel("cow");
        cowModel.setLocalTranslation(0, Planet.PLANET_RADIUS, 0);
        cow.attachChild(cowModel);
        cow.addControl(new CowControl());

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

        return satelliteNode;
    }

    //public Spatial createPlanet(){} TODO

    //public Spatial createShip(){} TODO
}
