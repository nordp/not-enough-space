package edu.chalmers.notenoughspace.assets;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;

/**
 * Created by Phnor on 2017-04-26.
 */
public class ModelLoaderFactory {

    private static AssetManager assetManager;
    private static ModelManager modelManager;
    private static IFontLoader fontManager;

    private ModelLoaderFactory(){}

    public static void setAssetManager(AssetManager assetManager) {
        ModelLoaderFactory.assetManager = assetManager;
    }

    public static IModelLoader getModelLoader() {
        if (modelManager == null) {
           modelManager= new ModelManager(assetManager);
           return modelManager;
        } else {
            return modelManager;
        }
    }

    public static IFontLoader getFontLoader() {
        if (fontManager == null) {
            fontManager = new FontManager(assetManager);
            return fontManager;
        } else {
            return fontManager;
        }
    }

    }

