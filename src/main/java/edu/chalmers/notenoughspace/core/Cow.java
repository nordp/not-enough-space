package edu.chalmers.notenoughspace.core;

import com.jme3.math.FastMath;

public class Cow {

    public final static float REACTION_DISTANCE = 3f;
    public static final float SPRINT_SPEED = 0.3f;
    public final static float MAX_DIR = 3;
    public final static int SPRINT_COOLDOWN = 200;
    public final static int MAX_STAMINA = 200;

    public static final float MIN_WALKSPEED = 0.01f;
    public static final float MAX_WALKSPEED = 0.15f;
    /**
     * Chance in 100 that cow should change speed
     */
    public static final int CHANGE_SPEED_CHANCE = 2;

    private float walkDir;
    private float speed;
    private int stamina;
    private CowMood mood;

    public Cow(){
        mood = CowMood.CALM;
        stamina = MAX_STAMINA;
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

    public float getSpeed() {
        return speed;
    }

    public void setWalkDir(float walkDir) {
        this.walkDir = walkDir;
    }

    public void updateState(float distance) {
        updateMood(distance);
        updateSpeed();
    }

    private void updateSpeed() {
        if (FastMath.nextRandomFloat()*100 < CHANGE_SPEED_CHANCE) {
            speed = FastMath.nextRandomFloat() * (MAX_WALKSPEED - MIN_WALKSPEED) + MIN_WALKSPEED;
        }
    }

    private void updateMood(float distanceFromShip){
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

}