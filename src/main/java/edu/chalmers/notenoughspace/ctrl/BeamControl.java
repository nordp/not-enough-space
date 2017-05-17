package edu.chalmers.notenoughspace.ctrl;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.debug.WireBox;
import edu.chalmers.notenoughspace.event.BeamToggleEvent;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * Created by Vibergf on 03/04/2017.
 */
public class BeamControl extends AbstractControl {

    public BeamControl(){
        Bus.getInstance().register(this);
    }

    @Subscribe
    public void beamToggleEvent(BeamToggleEvent event){
        if(event.getEnabled())
            spatial.setCullHint(Spatial.CullHint.Never);
        else
            spatial.setCullHint(Spatial.CullHint.Always);
    }
boolean tmp = true;
    protected void controlUpdate(float v) {

    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
