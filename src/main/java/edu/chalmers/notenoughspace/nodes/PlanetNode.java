package edu.chalmers.notenoughspace.nodes;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;
import edu.chalmers.notenoughspace.NodeFactory;
import edu.chalmers.notenoughspace.model.Planet;

import static edu.chalmers.notenoughspace.model.Planet.*;

public class PlanetNode extends Node {
    private AssetManager assetManager;
    private NodeFactory nodeFactory;

    private Node population;

    private Planet planet;

    public PlanetNode(Planet planet, AssetManager assetManager, ShipNode shipNode){
        this.planet = planet;
        Sphere shape = new Sphere(100, 100, PLANET_RADIUS);
        shape.setTextureMode(Sphere.TextureMode.Projected);
        TangentBinormalGenerator.generate(shape);
        Geometry model = new Geometry("planet", shape);
        model.setMesh(shape);
        model.setMaterial(assetManager.loadMaterial("Materials/PlanetMaterial.j3m"));
        attachChild(model);
        this.assetManager = assetManager;
        population = new Node();
        attachChild(population);
        this.nodeFactory = new NodeFactory();
    }

    public void populate(int nCow, int nJunk, int nSatellite){
        population.detachAllChildren();
        for (int i = 0; i < nCow; i++){
            Spatial c = nodeFactory.createCow();

            //TODO Implement random placing
            c.rotate(i,i,i);
            population.attachChild(c);
        }

        for (int i = 0; i < nJunk; i++){
            population.attachChild(nodeFactory.createJunk());
        }

        for (int i = 0; i < nSatellite; i++){
            population.attachChild(nodeFactory.createSatellite());
        }

    }
}
