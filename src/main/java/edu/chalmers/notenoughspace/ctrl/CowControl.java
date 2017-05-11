package edu.chalmers.notenoughspace.ctrl;

import com.google.common.eventbus.Subscribe;
import com.jme3.animation.AnimControl;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.Cow;
import edu.chalmers.notenoughspace.core.CowMood;

public class CowControl extends AbstractControl {
    private Cow cow;

    public CowControl(Cow cow) {
        this.cow = cow;
    }

    @Override
    protected void controlUpdate(float tpf) {
        JMEInhabitant body = new JMEInhabitant(spatial);
        JMEInhabitant ship = new JMEInhabitant(NodeUtil.getRoot(spatial).getChild("ship"));

        cow.update(body, ship, tpf);

        //TODO: This animation adjusting code should not be in the control. (But where should it be??)
        if (cow.getMood() == CowMood.SCARED) {
            Node cowNode = (Node) spatial;
            cowNode.getChild(0).getControl(AnimControl.class).getChannel(0).setSpeed(10);
        } else {
            Node cowNode = (Node) spatial;
            cowNode.getChild(0).getControl(AnimControl.class).getChannel(0).setSpeed(2.5f);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
