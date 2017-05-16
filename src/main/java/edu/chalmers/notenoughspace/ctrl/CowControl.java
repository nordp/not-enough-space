package edu.chalmers.notenoughspace.ctrl;

import com.google.common.eventbus.Subscribe;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.material.MaterialDef;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Transform;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.debug.WireBox;
import edu.chalmers.notenoughspace.core.BeamState;
import edu.chalmers.notenoughspace.core.Cow;
import edu.chalmers.notenoughspace.core.CowMood;
import edu.chalmers.notenoughspace.event.BeamEnteredEvent;
import edu.chalmers.notenoughspace.event.BeamExitedEvent;
import edu.chalmers.notenoughspace.event.Bus;

public class CowControl extends AbstractControl {
    private Cow cow;
    private float ORIGINAL_SCALE;

    public CowControl(Cow cow) {
        this.cow = cow;
        ORIGINAL_SCALE = 0;
    }

    @Override
    protected void controlUpdate(float tpf) {
        JMEInhabitant ship = new JMEInhabitant(NodeUtil.getRoot(spatial).getChild("ship"));

        cow.update(ship, tpf);

        Spatial model = ((Node) spatial).getChild(0);
        if (ORIGINAL_SCALE == 0) {
            ORIGINAL_SCALE = model.getLocalScale().x;
        }

        //TODO: Same here...
        if (cow.isInBeam() == BeamState.IN_BEAM) {
            AnimControl control = model.getControl(AnimControl.class);
            AnimChannel channel = control.getChannel(0);
            if (!channel.getAnimationName().equals("jigger")) {
                channel.setAnim("jigger");
            }
            channel.setSpeed(10);
            model.setLocalScale(model.getLocalScale().x - 0.0005f);

        } else {
            model.setLocalScale(ORIGINAL_SCALE);
            AnimControl control = model.getControl(AnimControl.class);
            AnimChannel channel = control.getChannel(0);
            if (!channel.getAnimationName().equals("WalkCycle")) {
                channel.setAnim("WalkCycle");
            }

            //TODO: This animation adjusting code should not be in the control. (But where should it be??)
            if (cow.getMood() == CowMood.SCARED) {
                Node cowNode = (Node) spatial;
                cowNode.getChild(0).getControl(AnimControl.class).getChannel(0).setSpeed(10);
            } else {
                Node cowNode = (Node) spatial;
                cowNode.getChild(0).getControl(AnimControl.class).getChannel(0).setSpeed(2.5f);
            }
        }
        //Collision
        CollisionResults results = new CollisionResults();
//        BoundingVolume bv = NodeUtil.getRoot(spatial).getChild("beamModel").getWorldBound();
        BoundingVolume bv = ((Node) spatial).getChild(0).getWorldBound();
        (NodeUtil.getRoot(spatial).getChild("beamModel")).collideWith(bv, results);

        if (NodeUtil.getRoot(spatial).getChild("beamModel").getCullHint() == Spatial.CullHint.Never && results.size() > 0) {
            if(cow.isInBeam() == BeamState.NOT_IN_BEAM){
                cow.enterBeam();
//                ((Node) spatial).getChild(0).rotate(0f, FastMath.DEG_TO_RAD*180f, 0f);
            }
        }else{
            if(cow.isInBeam() == BeamState.IN_BEAM){
                cow.exitBeam();
//                ((Node) spatial).getChild(0).rotate(0f, FastMath.DEG_TO_RAD*180f, 0f);
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
