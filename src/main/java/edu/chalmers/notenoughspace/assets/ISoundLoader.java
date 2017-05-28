package edu.chalmers.notenoughspace.assets;

import com.jme3.audio.AudioNode;

/**
 * Interface for sound loader.
 */
public interface ISoundLoader {

    AudioNode loadSound(String soundId);

}
