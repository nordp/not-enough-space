package edu.chalmers.notenoughspace.assets;

import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

/**
 * Interface for sound loader.
 */
public interface ISoundLoader {

    AudioNode loadSound(String soundId);

}
