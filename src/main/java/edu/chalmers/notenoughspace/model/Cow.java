package edu.chalmers.notenoughspace.model;

public class Cow {

    public final static float REACTION_DISTANCE = 3f;
    public final static float SPEED = 0.1f;
    public static final float SPRINT_SPEED = 0.3f;
    public final static float MAX_DIR = 3;
    public final static int SPRINT_COOLDOWN = 200;
    public final static int MAX_STAMINA = 200;

    public static final int MIN_WALKSPEED = 10;
    public static final int MAX_WALKSPEED = 100;

    private float walkDir;
    private int stamina;
    private CowMood mood;

    public Cow(){
        mood = CowMood.CALM;
        stamina = MAX_STAMINA;
    }

    public void updateMood(float distanceFromShip){
        if (distanceFromShip < REACTION_DISTANCE) {
            mood = CowMood.SCARED;
        } else {
            mood = CowMood.CALM;
        }
        if (stamina < 0){
            mood = CowMood.TIRED;
            if (stamina < -SPRINT_COOLDOWN){
                stamina = MAX_STAMINA;
            }
        }
    }

    public void reduceStamina(){
        stamina--;
    }

    public CowMood getMood(){
        return this.mood;
    }

    public float getWalkDir() {
        return walkDir;
    }

    public void setWalkDir(float walkDir) {
        this.walkDir = walkDir;
    }
}