package edu.chalmers.notenoughspace.core;

import com.jme3.math.FastMath;
import edu.chalmers.notenoughspace.event.AttachedEvent;
import edu.chalmers.notenoughspace.event.Bus;

import javax.vecmath.Vector3f;

public class Cow implements Spatial3D, Beamable{

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

    private Vector3f direction;
    private float speed;
    private int stamina;
    private CowMood mood;
    private BeamState beamState;

    public Cow(Spatial3D parent){
//        super(parent);
        mood = CowMood.CALM;
        beamState = BeamState.NOT_IN_BEAM;
        stamina = MAX_STAMINA;
    }

    public void fireEvent(Spatial3D parent) {
        Bus.getInstance().post(new AttachedEvent(parent, this, true));
    }

    public void update(Vector3f position, Vector3f shipPosition) {
//        updateMood(distance);
//        updateSpeed();
//        updateDirection?
    }

    public void reduceStamina(){
        stamina--;
    }

    public CowMood getMood(){
        return this.mood;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
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

    public BeamState isInBeam() {
        return beamState;
    }

    public void setInBeam(BeamState beamState){
        this.beamState = beamState;
    }

    public int getWeight() {
        return 1;
    }
}