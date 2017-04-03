package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * Created by Vibergf on 03/04/2017.
 */
public class Beam extends Geometry {

    private boolean active = true;
    private BeamControl controller;

    public Beam(AssetManager assetManager){
        setBeamModel(assetManager);
        controller = new BeamControl();
        setActive(false);

        setLocalTranslation(0f, -0.4f, 0f); //Adjust according to beam and ship models.
    }

    private void setBeamModel(AssetManager assetManager){
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        setMesh(new Box(0.1f, 0.4f, 0.1f));
        setMaterial(mat);
    }

    public void setActive(boolean active) {
        if(this.active == active)
            return;
        this.active = active;
        if(active){
            setCullHint(CullHint.Never);
            addControl(controller);
        }else{
            setCullHint(CullHint.Always);
            removeControl(controller);
        }
    }

    public boolean isActive() {
        return active;
    }
}
