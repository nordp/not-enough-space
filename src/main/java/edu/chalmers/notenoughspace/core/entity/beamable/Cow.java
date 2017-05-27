package edu.chalmers.notenoughspace.core.entity.beamable;

import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * Happily unaware cow walking randomly around the planet until
 * the ship gets close and it starts to panic. Can be beamed and stored by the ship.
 */
public class Cow extends BeamableEntity {

    public static final float REACTION_DISTANCE = 3f;

    public static final float SPRINT_SPEED = 0.3f;
    public static final float MAX_TURNING_DIR = 90;
    public static final int SPRINT_COOLDOWN = 3000;
    public static final int MAX_STAMINA = 3000;
    public static final int STAMINA_REDUCTION = 1000;
    public static final float MIN_WALKSPEED = 0.01f;
    public static final float MAX_WALKSPEED = 0.15f;
    public static final int CHANGE_SPEED_CHANCE = 2;
    public static final int CHANGE_DIRECTION_CHANCE = 2;

    public static final float MIN_SIZE = 0.70f;
    public static final float MAX_SIZE = 1.50f;

    public static final float BASE_WEIGHT = 1f;
    public static final float BASE_POINTS = 1f;

    //Control variables for golden cows.
    public static final int GOLD_CHANCE = 5;
    public static final float GOLD_SIZE = 0.60f;
    public static final float GOLD_POINTS_MODIFIER = 5f;
    public static final float GOLD_SPEED_MODIFIER = 2f;


    private float walkDir;
    private float speed;
    private int stamina;
    private CowMood mood;

    //Size will determine the scale, weight and points modifiers for the cow, unless it's a golden cow.
    private float sizeModifier;
    private float pointsModifier;
    private float speedModifier;

    private boolean golden;


    public Cow(){
        mood = CowMood.CALM;
        stamina = MAX_STAMINA;
        setModifiers();

        Bus.getInstance().post(new EntityCreatedEvent(this));
    }


    protected void onPlanetaryInhabitantAttached(){
        Entity.randomizeDirection(body);
    }

    public void update(PlanetaryInhabitant shipBody, float tpf) {
        updateMood(body.distanceTo(shipBody));
        updateSpeed();
        updateDirection(shipBody, tpf);
    }

    public CowMood getMood(){
        return this.mood;
    }

    public float getWeight() { // TODO the whole weight thing, along with special cows
        return BASE_WEIGHT * sizeModifier;
    }

    public float getPoints() {
        return BASE_POINTS * pointsModifier;
    }

    public float getSize(){
        return sizeModifier;
    }

    public boolean isGolden(){
        return golden;
    }


    private void setModifiers() {
        if (Math.random() * 100 < GOLD_CHANCE) {
            golden = true;
            sizeModifier = GOLD_SIZE;
            pointsModifier = GOLD_POINTS_MODIFIER;
            speedModifier = GOLD_SPEED_MODIFIER;
        } else {
            golden = false;
            float size = (float) Math.random() * (MAX_SIZE - MIN_SIZE) + MIN_SIZE;
            sizeModifier = size;
            pointsModifier = size;
            speedModifier = MAX_SIZE + MIN_SIZE - size; //The bigger the slower.
        }
    }

    private void updateMood(float distanceFromShip){
        if (stamina < 0){
            boolean fullyRecovered = stamina < -SPRINT_COOLDOWN;    //Stamina keeps dropping as cow recovers.
            if (fullyRecovered){
                stamina = MAX_STAMINA;
            }else{
                mood = CowMood.TIRED;
                return;
            }
        }

        if (distanceFromShip < REACTION_DISTANCE) {
            mood = CowMood.SCARED;
        } else {
            mood = CowMood.CALM;
        }
    }

    private void updateSpeed() {
        if (Math.random() * 100 < CHANGE_SPEED_CHANCE) {
            float randomSpeed = (float) Math.random() * (MAX_WALKSPEED - MIN_WALKSPEED) + MIN_WALKSPEED;
            speed = randomSpeed * speedModifier;
        }
    }

    private void updateDirection(PlanetaryInhabitant ship, float tpf){
        if (isInBeam()) {
            return;
        }

        float altitude = body.getLocalTranslation().y;
        if (altitude > Planet.PLANET_RADIUS) {
            gravitate();
            return;
        }

        switch (mood){
            case CALM:
                if ((float) Math.random() * 100 < CHANGE_DIRECTION_CHANCE) {
                    float randomAngle = (float) Math.random()*MAX_TURNING_DIR - MAX_TURNING_DIR/2;
                    walkDir = (float) Math.toRadians(randomAngle);
                }

                walk(speed * tpf);
                turn(walkDir * tpf);

                break;

            case SCARED:
                //"Should I turn left or right?"
                PlanetaryInhabitant left = body.clone();
                PlanetaryInhabitant right = body.clone();

                left.rotateModel((float) Math.toRadians(MAX_TURNING_DIR * tpf));
                left.rotateForward(speed * tpf);

                right.rotateModel((float) Math.toRadians(-MAX_TURNING_DIR * tpf));
                right.rotateForward(speed * tpf);

                float sprintDir;
                if (left.distanceTo(ship) < right.distanceTo(ship)){
                    sprintDir = -MAX_TURNING_DIR;
                } else {
                    sprintDir = MAX_TURNING_DIR;
                }

                walk(SPRINT_SPEED * speedModifier * tpf);
                turn((float)Math.toRadians(sprintDir * tpf));

                stamina -= STAMINA_REDUCTION * tpf;

                break;

            case TIRED:
                walk(speed/2 * tpf);
                turn((float) Math.toRadians(walkDir/2 * tpf));

                stamina -= STAMINA_REDUCTION * tpf; //Keeps on dropping until completely cooled down.

                break;
        }
    }

    private void walk(float distance) {
        body.rotateForward(distance);
    }

    private void turn(float angle) {
        body.rotateModel(angle);
    }

}