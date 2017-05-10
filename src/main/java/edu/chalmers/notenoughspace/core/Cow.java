package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;

import javax.vecmath.Vector3f;

public class Cow implements Entity, Beamable{

    public final static float REACTION_DISTANCE = 3f;
    public static final float SPRINT_SPEED = 0.3f;
    public final static float MAX_DIR = 90;
    public final static int SPRINT_COOLDOWN = 200;
    public final static int MAX_STAMINA = 200;

    public static final float MIN_WALKSPEED = 0.01f;
    public static final float MAX_WALKSPEED = 0.15f;
    /**
     * Chance in 100 that cow should change speed
     */
    public static final int CHANGE_SPEED_CHANCE = 2;
    public static final int CHANGE_DIRECTION_CHANCE = 2;

    private float walkDir;
    private float speed;
    private int stamina;
    private CowMood mood;
    private BeamState beamState;

    public Cow(){
//        super(parent);
        mood = CowMood.CALM;
        beamState = BeamState.NOT_IN_BEAM;
        stamina = MAX_STAMINA;
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }

    public void update(PlanetaryInhabitant body, PlanetaryInhabitant ship, float tpf) {
        updateMood(body.distance(ship));
        updateSpeed();
        updateDirection(body, ship, tpf);
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

    private void updateSpeed() {
        if (Math.random()*100 < CHANGE_SPEED_CHANCE) {
            speed = (float)Math.random() * (MAX_WALKSPEED - MIN_WALKSPEED) + MIN_WALKSPEED;
        }
    }

    private void updateDirection(PlanetaryInhabitant body, PlanetaryInhabitant ship, float tpf){
        switch (mood){
            case CALM:

                float rand = (float) Math.random();
                if (rand*100 < CHANGE_DIRECTION_CHANCE) {
                    setWalkDir((float)Math.toRadians((Math.random()*MAX_DIR-MAX_DIR/2)));
                }
                //Walk
                body.rotateForward(speed * tpf);
                body.rotateModel(walkDir*tpf);

                break;

            case SCARED:
                /*Vector3f distanceVector = shipPos.subtract(cowPos);
                Vector3f projectionVector = distanceVector.project(cowPos);
                Vector3f newZAxis = distanceVector.subtract(projectionVector).negate();
                spatial.lookAt(newZAxis, cowPos);
                spatial.rotate((FastMath.DEG_TO_RAD*-60),0,0);
                */


                //Turn left or right?
                PlanetaryInhabitant left = body.clone();
                PlanetaryInhabitant right = body.clone();

                left.rotateModel((float)Math.toRadians(MAX_DIR * tpf));
                left.rotateForward(speed * tpf);

                right.rotateModel((float)Math.toRadians(-MAX_DIR * tpf));
                right.rotateForward(speed * tpf);

                float sprintDir;
                if (left.distance(ship) < right.distance(ship)){
                    sprintDir = -MAX_DIR;
                } else {
                    sprintDir = MAX_DIR;
                }

                body.rotateForward(SPRINT_SPEED * tpf);
                body.rotateModel((float)Math.toRadians(sprintDir * tpf));
                reduceStamina();
                break;

            case TIRED:
                //Walk
                body.rotateForward(speed/2 * tpf);
                body.rotateModel((float)Math.toRadians(walkDir/2 * tpf));
                reduceStamina();
                break;
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

    public float getSpeed() {
        return speed;
    }

    public void setWalkDir(float walkDir) {
        this.walkDir = walkDir;
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