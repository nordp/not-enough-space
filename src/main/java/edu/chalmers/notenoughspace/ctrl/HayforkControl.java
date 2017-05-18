package edu.chalmers.notenoughspace.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.Hayfork;
import edu.chalmers.notenoughspace.core.Ship;

/**
 * Created by Sparven on 2017-05-15.
 */
public class HayforkControl extends AbstractControl {
    private Hayfork hayfork;
    private Ship ship;

    public HayforkControl(Hayfork hayfork) {
        this.hayfork = hayfork;
    }

    protected void controlUpdate(float tpf) {
        hayfork.update(new JMEInhabitant(ControlUtil.getRoot(spatial).getChild("ship")), tpf);
    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
