package edu.chalmers.notenoughspace.ctrl;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.entity.enemy.Farmer;

/**
 * Control responsible for telling the farmer when to update and when and how farmer related
 * animations and sounds should be played.
 */
public class FarmerControl extends DetachableControl {

    private final Farmer farmer;

    public FarmerControl(Farmer farmer){
        this.farmer = farmer;
    }


    protected void controlUpdate(float tpf) {
        JMEInhabitant ship = ControlUtil.getShip(spatial);

        farmer.update(ship, tpf);
        setAnimation();
    }


    private void setAnimation() {
        setAngryAnimation();
    }   //Prepared for possibly adding more animations.

    private void setAngryAnimation() {
        AnimControl control = getModel().getControl(AnimControl.class);
        AnimChannel channel = control.createChannel();

        channel.setAnim("run.001");
        channel.setSpeed(8f);

        playAngryFarmerSound();
    }


    private void playAngryFarmerSound() {
        if (!isTalking()) {
            ((AudioNode)((Node) spatial).getChild("audio")).play();
        }
    }

    private boolean isTalking() {
        AudioNode angryFarmerSound = (AudioNode)((Node) spatial).getChild("audio");
        return angryFarmerSound.getStatus() == AudioSource.Status.Playing;
    }

    private Spatial getModel() {
        return ((Node) spatial).getChild(0);
    }

}
