package edu.chalmers.notenoughspace.view;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.core.Storage;

/**
 * Created by Phnor on 2017-05-01.
 */
public class HUDNode extends Node {

    BitmapText timerText;
    BitmapText scoreText;
    BitmapText weightText;

    public HUDNode(int height, int width){
        //elapsedTime = 0;
        BitmapFont font = ModelLoaderFactory.getFontLoader().loadFont();
        timerText = new BitmapText(font);
        timerText.setSize(50);
        updateTimer(0);
        timerText.move(width/2-timerText.getLineWidth()/2, height, 0);
        attachChild(timerText);
        
        
        scoreText = new BitmapText(font);
        scoreText.setText("Poäng: NULL");
        scoreText.setSize(25);
        scoreText.move(0,height,0);
        attachChild(scoreText);

        weightText = new BitmapText(font);
        weightText.setText("NULL kg");
        weightText.setSize(25);
        weightText.move(0,height-scoreText.getLineHeight(),0);
        attachChild(weightText);
    }


    public void updateTimer(float seconds){             //TODO: Make HUD listen to changes in model by observer pattern
        timerText.setText("Time left: " +
                toTimeFormat(seconds));
    }

    public void updateStorage(Storage storage){
        scoreText.setText("Poäng: " + storage.calculateScore());
        weightText.setText(storage.calculateWeight() + "kg");
    }

    /**
     * Converts a given number of seconds into standard
     * digital clock format: mm:ss:hh.
     * @param seconds The number of seconds to convert. If negative the time 00:00:00 is returned.
     */
    private static String toTimeFormat(float seconds) {
        if (seconds < 0) {
            seconds = 0;
        }

        int m = (int) seconds / 60;
        int s = (int) seconds % 60;
        int h = (int) ((seconds*100) % 100);

        String mm = toTwoDigitsFormat(m);
        String ss = toTwoDigitsFormat(s);
        String hh = toTwoDigitsFormat(h);

        return mm + ":" + ss + ":" + hh;
    }

    private static String toTwoDigitsFormat(int number) {
        if (number < 10) {
            return "0" + number;
        }
        return "" + number;
    }

}
