package edu.chalmers.notenoughspace.view;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.util.StringFormatUtil;

/**
 * Created by Phnor on 2017-05-01.
 */
public class HUDNode extends Node {

    BitmapText timerText;

    public HUDNode(int height, int width){
        //elapsedTime = 0;
        BitmapFont font = ModelLoaderFactory.getFontLoader().loadFont();
        timerText = new BitmapText(font);
        timerText.setSize(50);
        updateTimer(0);
        timerText.move(width/2-timerText.getLineWidth()/2, height, 0);
        attachChild(timerText);
        
        
        BitmapText txt = new BitmapText(ModelLoaderFactory.getFontLoader().loadFont());
        txt.setText("Hello HUD");
        txt.setSize(50);
        txt.move(0,height,0);
        attachChild(txt);
    }


    public void updateTimer(float seconds){             //TODO: Make HUD listen to changes in model by observer pattern
        timerText.setText("Time left: " +
                StringFormatUtil.toTimeFormat(seconds));
    }

}
