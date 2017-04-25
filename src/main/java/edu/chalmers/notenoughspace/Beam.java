package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.sun.org.apache.bcel.internal.generic.FADD;
import edu.chalmers.notenoughspace.Model.Ship;

/**
 * Created by Vibergf on 03/04/2017.
 */
public class Beam extends Node {

    private BeamControl controller;

    private Ship ship;

    public Beam(Ship ship, AssetManager assetManager){
        this.ship = ship;
        setBeamModel(assetManager);
        controller = new BeamControl();
        setActive(false);
    }

    private void setBeamModel(AssetManager assetManager){
        Geometry model = (Geometry) assetManager.loadModel("Models/beam.obj");
//        model.setMaterial(assetManager.loadMaterial("Materials/CowMaterial.j3m"));
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(0.9f, 0.9f, 0.3f, 0.6f));
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        model.setMaterial(mat);
        model.setQueueBucket(RenderQueue.Bucket.Transparent);

//        model.scale(0.05f);
//        model.rotate(-FastMath.HALF_PI, 0f, 0f);
        model.setLocalTranslation(0f, 0.24f, 0f);
        attachChild(model);
    }

    public void setActive(boolean active) {
        if(ship.isBeamActive() == active)
            return;
        ship.setBeamActive(active);
        if(active){
            setCullHint(CullHint.Never);
            addControl(controller);
        }else{
            setCullHint(CullHint.Always);
            removeControl(controller);
        }
    }
}
