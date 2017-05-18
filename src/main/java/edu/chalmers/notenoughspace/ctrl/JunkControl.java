package edu.chalmers.notenoughspace.ctrl;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.BeamState;
import edu.chalmers.notenoughspace.core.Junk;

/**
 * Created by Phnor on 2017-05-09.
 */
public class JunkControl extends AbstractControl {

    private Junk junk;

    public JunkControl(Junk junk){
        this.junk = junk;
    }

    protected void controlUpdate(float v) {

        boolean colliding = ControlUtil.checkCollision(((Node) spatial).getChild(0), (ControlUtil.getRoot(spatial).getChild("beamModel")));

        if (colliding && ControlUtil.getRoot(spatial).getChild("beamModel").getCullHint() == Spatial.CullHint.Never) {
            if(junk.isInBeam() == BeamState.NOT_IN_BEAM){
                junk.enterBeam();
//                ((Node) spatial).getChild(0).rotate(0f, FastMath.DEG_TO_RAD*180f, 0f);
            }
        }else{
            junk.update(); //Gravitates the junk
            if(junk.isInBeam() == BeamState.IN_BEAM){
                junk.exitBeam();
//                ((Node) spatial).getChild(0).rotate(0f, FastMath.DEG_TO_RAD*180f, 0f);
            }
        }
    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
