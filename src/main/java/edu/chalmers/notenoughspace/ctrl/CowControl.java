package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.Cow;
import edu.chalmers.notenoughspace.util.NodeUtil;

import static edu.chalmers.notenoughspace.core.Cow.*;

import javax.vecmath.Vector3f;

class CowControl extends AbstractControl {
    private Cow cow;

    public CowControl(Cow cow) {
        this.cow = cow;
    }

    @Override
    protected void controlUpdate(float tpf) {
        JMEInhabitant body = new JMEInhabitant(spatial);
        JMEInhabitant ship = new JMEInhabitant(NodeUtil.getRoot(spatial).getChild("ship"));

        cow.update(body, ship, tpf);

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
