package edu.chalmers.notenoughspace.ctrl;

import com.google.common.eventbus.Subscribe;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.event.BeamToggleEvent;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * Control responsible for deciding when the beam should be visible and when beam sounds should be played.
 */
public class BeamControl extends DetachableControl {


    public BeamControl(){
        Bus.getInstance().register(this);
    }


    @Subscribe
    public void beamToggleEvent(BeamToggleEvent event){
        boolean beamSwitchedOn = event.getEnabled();

        if(beamSwitchedOn) {
            showBeam();
            resumeBuzzingSound();
        } else {
            hideBeam();
            pauseBuzzingSound();
        }
    }

    @Override
    public void onDetach() {
        Bus.getInstance().unregister(this);
    }


    private void showBeam() {
        spatial.setCullHint(Spatial.CullHint.Never);
    }

    private void hideBeam() {
        spatial.setCullHint(Spatial.CullHint.Always);
    }

    private void resumeBuzzingSound() {
        ((AudioNode) ((Node) spatial).getChild("audio")).play();
    }

    private void pauseBuzzingSound() {
        ((AudioNode) ((Node) spatial).getChild("audio")).stop();
    }

}
