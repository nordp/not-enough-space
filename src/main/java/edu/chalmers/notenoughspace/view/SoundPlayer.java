package edu.chalmers.notenoughspace.view;

import com.jme3.audio.AudioNode;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;

/**
 * Singleton class for playing common (non-positional) sounds without having to reload them each time.
 */
public class SoundPlayer {

    private static SoundPlayer player;

    public static SoundPlayer getInstance() {
        if (player == null) {
            player = new SoundPlayer();
        }
        return player;
    }

    public void play(String soundId) {
        if (soundId.equals("beamed")) {
            AudioNode beamedAudio = ModelLoaderFactory.getSoundLoader().loadSound("beamed");
            beamedAudio.setLooping(false);
            beamedAudio.play();
        } else if (soundId.equals("explosion")) {
            AudioNode explosionAudio = ModelLoaderFactory.getSoundLoader().loadSound("explosion");
            explosionAudio.setLooping(false);
            explosionAudio.play();
        } else if (soundId.equals("hayforkHit")) {
            AudioNode explosionAudio = ModelLoaderFactory.getSoundLoader().loadSound("hayforkHit");
            explosionAudio.setLooping(false);
            explosionAudio.play();
        }
    }
}
