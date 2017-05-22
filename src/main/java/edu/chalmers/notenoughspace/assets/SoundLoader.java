package edu.chalmers.notenoughspace.assets;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Created by Sparven on 2017-05-20.
 */
class SoundLoader implements ISoundLoader {

    private AssetManager assetManager;

    protected SoundLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public AudioNode loadSound(String soundId) {
        if (soundId.equals("farmer")) {
            return new AudioNode(assetManager,
                    "Sounds/angryFarmer.WAV", AudioData.DataType.Buffer);
        } else if (soundId.equals("cow")) {
            return new AudioNode(assetManager,
                    "Sounds/moo.WAV", AudioData.DataType.Buffer);
        } else if (soundId.equals("cow2")) {
            return new AudioNode(assetManager,
                    "Sounds/moo2.WAV", AudioData.DataType.Buffer);
        } else if (soundId.equals("cow3")) {
            return new AudioNode(assetManager,
                    "Sounds/moo3.WAV", AudioData.DataType.Buffer);
        } else if (soundId.equals("beam")) {
            return new AudioNode(assetManager,
                    "Sounds/beamBuzz.WAV", AudioData.DataType.Buffer);
        } else if (soundId.equals("beamed")) {
            return new AudioNode(assetManager,
                    "Sounds/objectBeamed.WAV", AudioData.DataType.Buffer);
        } else if (soundId.equals("hayforkThrown")) {
            return new AudioNode(assetManager,
                    "Sounds/swish.WAV", AudioData.DataType.Stream);
        } else if (soundId.equals("explosion")) {
            return new AudioNode(assetManager,
                    "Sounds/explosion.WAV", AudioData.DataType.Buffer);
        } else if (soundId.equals("hayforkHit")) {
            return new AudioNode(assetManager,
                    "Sounds/hayforkHit.WAV", AudioData.DataType.Buffer);
        }

        throw new IllegalArgumentException("no such sound in sound package");
    }
    

}
