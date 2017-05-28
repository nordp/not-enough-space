package edu.chalmers.notenoughspace.view.scene;

import com.jme3.audio.AudioNode;
import edu.chalmers.notenoughspace.assets.AssetLoaderFactory;
import edu.chalmers.notenoughspace.assets.ISoundLoader;

/**
 * Singleton class for playing common (non-positional) sounds.
 */
public class SoundPlayer {

    private static SoundPlayer player;

    private SoundPlayer(){}
    
    
    public static SoundPlayer getInstance() {
        if (player == null) {
            player = new SoundPlayer();
        }
        return player;
    }

    
    public void play(String soundID) {
        ISoundLoader soundLoader = AssetLoaderFactory.getSoundLoader();
        AudioNode audio = new AudioNode();
        
        if (soundID.equals("beamed")) {
            audio = soundLoader.loadSound("beamed");
            audio.setLooping(false);
        } else if (soundID.equals("explosion")) {
            audio = soundLoader.loadSound("explosion");
            audio.setLooping(false);
        } else if (soundID.equals("hayforkHit")) {
            audio = soundLoader.loadSound("hayforkHit");
            audio.setLooping(false);
        }

        audio.play();
    }

}
