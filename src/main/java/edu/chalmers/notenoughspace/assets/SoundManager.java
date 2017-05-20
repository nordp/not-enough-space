package edu.chalmers.notenoughspace.assets;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;

/**
 * Created by Sparven on 2017-05-20.
 */
public class SoundManager implements ISoundLoader {

    private AssetManager assetManager;

    protected SoundManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public AudioNode loadSound(String soundId) {
        if (soundId.equals("farmer")) {
            return new AudioNode(assetManager,
                    "Sounds/beep2.WAV");
        }

        throw new IllegalArgumentException("no such sound in sound package");
    }
}
