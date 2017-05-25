package edu.chalmers.notenoughspace.highscore;

/**
 * Created by juliaortheden on 2017-05-23.
 */

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.GameOverEvent;

import java.util.*;
import java.io.*;

public class HighScoreManager {
    // An arraylist of the type "score" we will use to work with the scores inside the class
    private ArrayList<Score> scores;
    private String newName = "hej";

    private static HighScoreManager highScoreManager;

    // The name of the file where the highscores will be saved
    private static final String HIGHSCORE_FILE = "scores.dat";

    //Initialising an in and outputStream for working with the file
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    private HighScoreManager() {
        //initialising the scores-arraylist
        scores = new ArrayList<Score>();
        Bus.getInstance().register(this);
    }

    public ArrayList<Score> getScores() {
        loadScoreFile();
        sort();
        return scores;
    }

    private void sort() {
        HighScoreComparator comparator = new HighScoreComparator();
        Collections.sort(scores, comparator);
    }

    public void addScoreToList(String name, int score) {
        loadScoreFile();
        scores.add(new Score(name, score));
        updateScoreFile();
    }

    @Subscribe
    public void levelOver(GameOverEvent event){addScoreToList(newName, (int) event.getPoints());}

    public void loadScoreFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
            scores = (ArrayList<Score>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[Laad] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Laad] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Laad] CNF Error: " + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                   // inputStream.flush();
                    inputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Laad] IO Error: " + e.getMessage());
            }
        }
    }

    public void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] Error: " + e.getMessage());
            }
        }
    }
    public String getHighScoreString() {
        String highScoreString = "";
        final int max = 10;

        ArrayList<Score> scores;
        scores = getScores();

        int i = 0;
        int x = scores.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
            highScoreString += (i + 1) + ".  " + scores.get(i).getName() + "  " + scores.get(i).getScore() + "\n";
            i++;
        }
        return highScoreString;
    }

    public static HighScoreManager getHighScoreManager(){
        if(highScoreManager == null){
            highScoreManager= new HighScoreManager();
        }
        return highScoreManager;
    }
}