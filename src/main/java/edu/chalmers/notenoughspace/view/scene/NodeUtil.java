package edu.chalmers.notenoughspace.view.scene;

import com.jme3.audio.AudioNode;
import com.jme3.effect.ParticleEmitter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

class NodeUtil {

    static void setUpAudioNode(AudioNode audioNode, float refDistance, float volume, boolean looping,
                                Node parentNode, String name) {
        audioNode.setPositional(true);  // Use 3D audio
        audioNode.setRefDistance(refDistance);  // Distance of 50% volume
        audioNode.setMaxDistance(1000f); // Stops going quieter
        audioNode.setVolume(volume);         // Default volume
        audioNode.setLooping(looping);

        Spatial point = parentNode.getChild(0);
        audioNode.setLocalTranslation(point.getLocalTranslation());
        audioNode.setLocalRotation(point.getLocalRotation());

        audioNode.setName(name);
        parentNode.attachChild(audioNode);
    }

    static void setUpAudioNode(AudioNode audioNode, float refDistance, float volume, boolean looping,
                                      Node parentNode) {
        setUpAudioNode(audioNode, refDistance, volume, looping, parentNode, "audio");
    }

    static void setUpEffectNode(ParticleEmitter emitter, Node parentNode){
        Spatial point = parentNode.getChild(0);
        emitter.setLocalTranslation(point.getLocalTranslation());
        emitter.move(0f, 0.2f, 0.1f);
        emitter.setLocalRotation(point.getLocalRotation());

        parentNode.attachChild(emitter);
    }
}
