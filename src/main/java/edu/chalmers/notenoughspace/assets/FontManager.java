package edu.chalmers.notenoughspace.assets;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;

/**
 * Created by Phnor on 2017-05-01.
 */
class FontManager implements IFontLoader {

    private AssetManager assetManager;

    protected FontManager(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    public BitmapFont loadFont() {
        return assetManager.loadFont("Interface/Fonts/Default.fnt");
    }
}
