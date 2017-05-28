package edu.chalmers.notenoughspace.view.scene;

import com.jme3.audio.AudioNode;
import com.jme3.effect.ParticleEmitter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Utility class for setting up sound and special effect nodes to be used in the game.
 */
class NodeUtil {

    static void setUpAudioNode(AudioNode audioNode, float refDistance, float volume,
                               boolean looping, Node parentNode, String name) {
        audioNode.setPositional(true);  //Use 3D audio.
        audioNode.setRefDistance(refDistance);  //Distance of 50% volume.
        audioNode.setMaxDistance(1000f); //Stops going quieter after this.
        audioNode.setVolume(volume);
        audioNode.setLooping(looping);

        Spatial emissionPoint = parentNode.getChild(0);
        audioNode.setLocalTranslation(emissionPoint.getLocalTranslation());
        audioNode.setLocalRotation(emissionPoint.getLocalRotation());

        audioNode.setName(name);
        parentNode.attachChild(audioNode);
    }

    static void setUpAudioNode(AudioNode audioNode, float refDistance, float volume, boolean looping,
                                      Node parentNode) {
        setUpAudioNode(audioNode, refDistance, volume, looping, parentNode, "audio");
    }

    static void setUpEffectNode(ParticleEmitter emitter, Node parentNode){
        Spatial emissionPoint = parentNode.getChild(0);
        emitter.setLocalTranslation(emissionPoint.getLocalTranslation());
        emitter.move(0f, 0.2f, 0.1f);
        emitter.setLocalRotation(emissionPoint.getLocalRotation());

        parentNode.attachChild(emitter);
    }

}
