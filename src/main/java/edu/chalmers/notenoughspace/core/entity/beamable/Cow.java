package edu.chalmers.notenoughspace.core.entity.beamable;

import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;

public class Cow extends BeamableEntity {

    public static final float REACTION_DISTANCE = 3f;
    public static final float SPRINT_SPEED = 0.3f;
    public static final float MAX_DIR = 90;
    public static final int SPRINT_COOLDOWN = 3000;
    public static final int MAX_STAMINA = 3000;
    public static final int STAMINA_REDUCTION = 1000;

    public static final float MIN_WALKSPEED = 0.01f;
    public static final float MAX_WALKSPEED = 0.15f;

    public static final float MIN_SIZE = 0.70f;
    public static final float MAX_SIZE = 1.50f;

    public static final float BASE_WEIGHT = 1f;
    public static final float BASE_POINTS = 1f;

    /**
     * Control variables for golden cows.
     */
    public static final int GOLD_CHANCE = 5;
    public static final float GOLD_SIZE = 0.60f;
    public static final float GOLD_POINTS_MODIFIER = 5f;
    public static final float GOLD_SPEED_MODIFIER = 2f;

    /**
     * Chance in 100 that cow should change speed or direction
     */
    public static final int CHANGE_SPEED_CHANCE = 2;
    public static final int CHANGE_DIRECTION_CHANCE = 2;

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

//        super(parent);
        mood = CowMood.CALM;
        stamina = MAX_STAMINA;

        if(Math.random()*100 < GOLD_CHANCE){
            golden = true;
            sizeModifier = GOLD_SIZE;
            pointsModifier = GOLD_POINTS_MODIFIER;
            speedModifier = GOLD_SPEED_MODIFIER;
        }else{
            golden = false;
            float size = (float)Math.random() * (MAX_SIZE - MIN_SIZE) + MIN_SIZE;
            sizeModifier = size;
            pointsModifier = size;
            speedModifier = -1 * (size - MAX_SIZE - MIN_SIZE); //Invert the size-speed relation
        }

        Bus.getInstance().post(new EntityCreatedEvent(this));
    }

    public void update(PlanetaryInhabitant ship, float tpf) {
        updateMood(body.distance(ship));
        updateSpeed();
        updateDirection(ship, tpf);
    }

    private void updateMood(float distanceFromShip){
        if (distanceFromShip < REACTION_DISTANCE) {
            mood = CowMood.SCARED;
        } else {
            mood = CowMood.CALM;
        }
        if (stamina < 0){
            if (stamina < -SPRINT_COOLDOWN){
                stamina = MAX_STAMINA;
            }else{
                mood = CowMood.TIRED;
            }
        }
    }

    private void updateSpeed() {
        if (Math.random()*100 < CHANGE_SPEED_CHANCE) {
            speed = ((float)Math.random() * (MAX_WALKSPEED - MIN_WALKSPEED) + MIN_WALKSPEED) * speedModifier;
        }
    }

    private void updateDirection(PlanetaryInhabitant ship, float tpf){
        if (isInBeam() == BeamState.IN_BEAM) {
            return;
        }

        if (body.getLocalTranslation().y > Planet.PLANET_RADIUS) {
            gravitate();
            return;
        }

        switch (mood){
            case CALM:

                float rand = (float) Math.random();
                if (rand*100 < CHANGE_DIRECTION_CHANCE) {
                    walkDir = (float)Math.toRadians((Math.random()*MAX_DIR-MAX_DIR/2));
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

                body.rotateForward(SPRINT_SPEED * speedModifier * tpf);
                body.rotateModel((float)Math.toRadians(sprintDir * tpf));
                stamina -= STAMINA_REDUCTION * tpf;
                break;

            case TIRED:
                //Walk
                body.rotateForward(speed/2 * tpf);
                body.rotateModel((float)Math.toRadians(walkDir/2 * tpf));
                stamina -= STAMINA_REDUCTION * tpf;
                break;
        }
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
}