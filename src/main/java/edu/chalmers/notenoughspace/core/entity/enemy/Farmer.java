package edu.chalmers.notenoughspace.core.entity.enemy;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.entity.ship.Health;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.*;

import java.util.Random;

/**
 *  Angry farmer who runs around the planet looking for the ship. If the ship is
 *  within a certain range, the farmer throws hayforks at it.
 */
public class Farmer extends Entity {

    private final static float AGGRO_DISTANCE = 10f;
    private final static float CIRCLE_DISTANCE = 4f;
    private final static float SPRINT_SPEED = 0.8f;
    private final static float TURN_RADIUS = 250;
    private final static float THROW_CHANCE = 1f;
    private final static float CHANGE_DIRECTION_CHANCE = 5f;

    private final Random pseudoThrowChance;
    private final Health health;

    private boolean runClockwise = false;
    private boolean canThrow = true;

    public Farmer(){
        super(new ZeroGravityStrategy());
        pseudoThrowChance = new Random();
        health = new Health(100, 100, 5);

        Bus.getInstance().post(new EntityCreatedEvent(this));
    }


    public void update(PlanetaryInhabitant ship, float tpf) {
        if (body.distanceTo(ship) <= AGGRO_DISTANCE) {
            chaseShip(ship, tpf);
            if (pseudoThrowChance.nextFloat() * 100 <= THROW_CHANCE && canThrow == true) {
                throwHayfork();
            }
            if (Math.random() * 100 <= CHANGE_DIRECTION_CHANCE) {
                runClockwise = !runClockwise;
            }
        } else {
            randomlyStrollAround(tpf);
        }
        increaseHealth(tpf);
    }

    private void chaseShip(PlanetaryInhabitant ship, float tpf) {
        float turnDir;

        //"Should I turn left or right?"
        PlanetaryInhabitant left = body.clone();
        PlanetaryInhabitant right = body.clone();

        left.rotateModel((float) Math.toRadians(TURN_RADIUS * tpf));
        left.rotateForward(SPRINT_SPEED * tpf);

        right.rotateModel((float) Math.toRadians(-TURN_RADIUS * tpf));
        right.rotateForward(SPRINT_SPEED * tpf);

        if(!runClockwise) {
            if (left.distanceTo(ship) < right.distanceTo(ship) && left.distanceTo(ship) > CIRCLE_DISTANCE) {
                turnDir = TURN_RADIUS * tpf;
            } else {
                turnDir = -TURN_RADIUS * tpf;
            }
        }else{
            if (right.distanceTo(ship) < left.distanceTo(ship) && right.distanceTo(ship) > CIRCLE_DISTANCE) {
                turnDir = -TURN_RADIUS * tpf;
            } else {
                turnDir = TURN_RADIUS * tpf;
            }
        }

        float angleToTurn = (float) Math.toRadians(turnDir);
        body.rotateModel(angleToTurn);
        body.rotateForward(SPRINT_SPEED * tpf);
    }

    private void randomlyStrollAround(float tpf) {
        if (Math.random() * 100 <= 10) {
            float randomDirection = (float) (Math.random()*180 - 180/2);
            float angleToTurn = 10 * tpf * (float) Math.toRadians(randomDirection);

            body.rotateModel(angleToTurn);
        }

        body.rotateForward(SPRINT_SPEED/3 * tpf);
    }

    private void throwHayfork() {
        new Hayfork(this);
    }

    public float getHealth() { return health.getCurrentHealthLevel();}

    @Subscribe
    public void ShootCollisionEvent(ShootCollisionEvent event){
        int damage = event.getDamage();
        health.modifyHealth(-damage);
        System.out.println("current health: " + health);
    }

    private void increaseHealth(float tpf){
        health.regenerate(tpf);
        if(health.getCurrentHealthLevel() > 50){
            canThrow = true;
        }
        }

    @Subscribe
    public void FarmerHealthEmptyEvent(FarmerHealthEmptyEvent event){
        canThrow = false;
        }

    }


