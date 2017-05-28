package edu.chalmers.notenoughspace.assets;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;

/**
 * Loads sound files for the game.
 */
class SoundLoader implements ISoundLoader {

    private final AssetManager assetManager;

    public SoundLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public AudioNode loadSound(String soundID) {

        if (soundID.equals("farmer")) {
            return new AudioNode(assetManager,
                    "Sounds/angryFarmer.WAV", AudioData.DataType.Buffer);
        } else if (soundID.equals("cow")) {
            return new AudioNode(assetManager,
                    "Sounds/moo.WAV", AudioData.DataType.Buffer);
        } else if (soundID.equals("cow2")) {
            return new AudioNode(assetManager,
                    "Sounds/moo2.WAV", AudioData.DataType.Buffer);
        } else if (soundID.equals("cow3")) {
            return new AudioNode(assetManager,
                    "Sounds/moo3.WAV", AudioData.DataType.Buffer);
        } else if (soundID.equals("beam")) {
            return new AudioNode(assetManager,
                    "Sounds/beamBuzz.WAV", AudioData.DataType.Buffer);
        } else if (soundID.equals("beamed")) {
            return new AudioNode(assetManager,
                    "Sounds/objectBeamed.WAV", AudioData.DataType.Buffer);
        } else if (soundID.equals("hayforkThrown")) {
            return new AudioNode(assetManager,
                    "Sounds/swish.WAV", AudioData.DataType.Stream);
        } else if (soundID.equals("explosion")) {
            return new AudioNode(assetManager,
                    "Sounds/explosion.WAV", AudioData.DataType.Buffer);
        } else if (soundID.equals("hayforkHit")) {
            return new AudioNode(assetManager,
                    "Sounds/hayforkHit.WAV", AudioData.DataType.Buffer);
        } else if (soundID.equals("brodyquest")) {
            return new AudioNode(assetManager, "Sounds/brodyquest.wav", AudioData.DataType.Buffer);
        } else {
            throw new IllegalArgumentException("No such sound listed in loader.");
        }
    }

}
