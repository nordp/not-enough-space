package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

import java.util.ArrayList;
import java.util.List;

public class Planet extends Node {
    private AssetManager assetManager;
    private CowFactory cowFactory;

    public final static float PLANET_RADIUS = 13f;

    private Node population;

    public Planet(AssetManager assetManager, Ship ship){
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
        this.cowFactory = new CowFactory(assetManager, ship, PLANET_RADIUS);            //Ship extends Node
    }

    public void populate(int nCow, int nJunk){
        population.detachAllChildren();
        for (int i = 0; i < nCow; i++){
            Cow c = cowFactory.createCow();

            //TODO Implement random placing
            c.rotate(i,i,i);
            population.attachChild(c);
        }

        for (int i = 0; i < nJunk; i++){
            //population.add(new Junk()); //TODO Implement Junk class
        }

    }
}
