package edu.chalmers.notenoughspace.nodes;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import edu.chalmers.notenoughspace.model.Ship;
import edu.chalmers.notenoughspace.ctrl.BeamControl;

/**
 * Created by Vibergf on 03/04/2017.
 */
public class BeamNode extends Node {

    private BeamControl controller;

    private Ship ship;

    public BeamNode(Ship ship, AssetManager assetManager){
        this.ship = ship;
        setBeamModel(assetManager);
        controller = new BeamControl();
        setActive(true);
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
