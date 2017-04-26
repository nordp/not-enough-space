package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;
import edu.chalmers.notenoughspace.Model.Planet;

import static edu.chalmers.notenoughspace.Model.Planet.*;

public class PlanetNode extends Node {
    private AssetManager assetManager;
    private CowFactory cowFactory;
    private Junk junk;
    private Satellite satellite;

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
        this.cowFactory = new CowFactory(assetManager, shipNode, PLANET_RADIUS);//ShipNode extends Node
        this.junk = new Junk(assetManager, PLANET_RADIUS);
        this.satellite = new Satellite(PLANET_RADIUS + 2, assetManager); //todo:find the right heigh to add to radius
    }

    public void populate(int nCow, int nJunk, int nSatellite){
        population.detachAllChildren();
        for (int i = 0; i < nCow; i++){
            Spatial c = cowFactory.createCow();

            //TODO Implement random placing
            c.rotate(i,i,i);
            population.attachChild(c);
        }

        for (int i = 0; i < nJunk; i++){
            population.attachChild(junk.createHouseModel());
        }

        for (int i = 0; i < nSatellite; i++){
            population.attachChild(satellite.createSatellite(assetManager));
        }

    }
}
