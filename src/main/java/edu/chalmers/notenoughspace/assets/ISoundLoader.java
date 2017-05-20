package edu.chalmers.notenoughspace.assets;

import com.jme3.audio.AudioNode;

/**
 * Created by Sparven on 2017-05-20.
 */
public interface ISoundLoader {

    public AudioNode loadSound(String soundId);
}
