package edu.chalmers.notenoughspace.highscore;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.GameOverEvent;

import java.util.*;
import java.io.*;

/**
 * Singleton class for storing and updating the high score table.
 */
public class HighScoreManager {

    private static final String HIGHSCORE_FILE = "scores.dat";
    private static final int NUMBER_OF_SCORES_TO_SHOW = 10;

    private static HighScoreManager highScoreManager;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private ArrayList<Score> scores;

    private HighScoreManager() {
        scores = loadScoreFile();
        Bus.getInstance().register(this);
    }


    public static HighScoreManager getHighScoreManager(){
        if (highScoreManager == null) {
            highScoreManager = new HighScoreManager();
        }

        return highScoreManager;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Score> scores){
        sort(scores);
        this.scores = scores;

        updateScoreFile();
    }

    public void clearScores(){
        scores.clear();

        updateScoreFile();
    }

    public void addScore(String name, int score) {
        scores.add(new Score(name, score));
        sort(scores);

        updateScoreFile();
    }

    public ArrayList<Score> loadScoreFile() {
        ArrayList<Score> scores = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(HIGHSCORE_FILE);
            inputStream = new ObjectInputStream(fileInputStream);

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
                    inputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Laad] IO Error: " + e.getMessage());
            }
        }

        return scores;
    }

    public void updateScoreFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(HIGHSCORE_FILE);
            outputStream = new ObjectOutputStream(fileOutputStream);

            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage() + ", the program will try and make a new file");
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
        ArrayList<Score> scores = getScores();

        int numberOfScores = scores.size();
        if (numberOfScores > NUMBER_OF_SCORES_TO_SHOW) {
            numberOfScores = NUMBER_OF_SCORES_TO_SHOW;
        }

        for (int i = 0; i < numberOfScores; i++) {
            String name = scores.get(i).getName();
            int score = scores.get(i).getScore();

            String row = (i + 1) + ".  " + name + "  " + score + "\n";

            highScoreString += row;
        }

        return highScoreString;
    }

    @Subscribe
    public void levelOver(GameOverEvent event){
        int score = event.getScore();
        addScore("ANONYMOUS", score);
    }


    private void sort(ArrayList<Score> scores) {
        HighScoreComparator comparator = new HighScoreComparator();
        Collections.sort(scores, comparator);
    }

}