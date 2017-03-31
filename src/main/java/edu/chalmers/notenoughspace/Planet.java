package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

import java.util.List;

public class Planet extends Geometry {
    public final static float PLANET_RADIUS = 3.2f;
    private List<Beamable> population;

    public Planet(AssetManager assetManager){
        Sphere shape = new Sphere(100, 100, PLANET_RADIUS);
        shape.setTextureMode(Sphere.TextureMode.Projected);
        TangentBinormalGenerator.generate(shape);
        Material sphereMat = new Material(assetManager,
            "Common/MatDefs/Light/Lighting.j3md");
        sphereMat.setBoolean("UseMaterialColors",true);
        sphereMat.setColor("Diffuse",ColorRGBA.White);
        sphereMat.setFloat("Shininess", 64f);  // [0,128]
        sphereMat.setTexture("DiffuseMap", assetManager.loadTexture("Textures/planet.jpg"));

        setMesh(shape);
        setMaterial(sphereMat);
    }

    public void populate(int nCow, int nJunk){
        population.clear();
        for (int i = 0; i < nCow; i++){
            Cow c = new Cow();
            getParent().attachChild(c);
            population.add(c);
        }

        for (int i = 0; i < nJunk; i++){
            //population.add(new Junk()); //TODO Implement Junk class
        }

    }
}
