package edu.chalmers.notenoughspace.ctrl;

import com.google.common.eventbus.Subscribe;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.ShieldActiveEvent;

/**
 * Control responsible for deciding when the shield model should be visible and when shield
 * sounds should be played.
 *
 */

public class ShieldControl extends DetachableControl {

    public ShieldControl(){
        Bus.getInstance().register(this);
    }

    private Spatial getModel() {
        return ((Node) spatial).getChild(0);
    }

    @Subscribe
    public void ShieldActiveEvent(ShieldActiveEvent event){
        boolean shieldSwitchedOn = event.getEnabled();

        if(shieldSwitchedOn) {
            showShield();
            resumeBuzzingSound();
        } else {
            hideShield();
            pauseBuzzingSound();
        }
    }
    @Override
    public void onDetach() {
        Bus.getInstance().unregister(this);
    }
    private void hideShield() {spatial.setCullHint(Spatial.CullHint.Always);}

    private void showShield() {
        spatial.setCullHint(Spatial.CullHint.Never);
    }

    private void resumeBuzzingSound() {
        ((AudioNode) ((Node) spatial).getChild("audio")).play();
    }

    private void pauseBuzzingSound() {
        ((AudioNode) ((Node) spatial).getChild("audio")).stop();
    }

}
