package edu.chalmers.notenoughspace.core.entity.ship;

import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.move.RealisticGravityStrategy;
import edu.chalmers.notenoughspace.event.*;

import javax.vecmath.Vector3f;

public class ShootWeapon extends Entity {

    private final static float SHOOT_SPEED = 8f;
    private final static int DAMAGE = 10;

    private final Entity shooter;
    private Vector3f direction;

    public ShootWeapon(Entity shooter) {
        super(new RealisticGravityStrategy());

        this.shooter = shooter;
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }

    public void hitSomething() {
        Vector3f pierceIntoVector = new Vector3f(direction.x, direction.y, direction.z);
        pierceIntoVector.normalize();
        pierceIntoVector.scale(0.32f);
        body.move(pierceIntoVector);

        Bus.getInstance().post(new ShootCollisionEvent(getID(), getDamage()));
    }

    public Entity getShooter() {
        return shooter;
    }

    public int getDamage() {return DAMAGE;}

    public Vector3f getDirection() {
        return direction;
    }

    private Vector3f distanceToMove(float tpf) {
        Vector3f distanceToMove = new Vector3f();
        distanceToMove.set(direction);
        distanceToMove.scale(tpf);
        return distanceToMove;
    }


        public void update(float tpf) {
            if (direction == null) {
                setDirectionTowards();

            }
            body.move(distanceToMove(tpf));
        }

        private void setDirectionTowards() {
            Vector3f myPosition = body.getPosition();
            Vector3f planetCenter = new Vector3f(0, 0, 0);
            body.setDirection(planetCenter);

            planetCenter.sub(myPosition);   //Results in vector directed directly towards the center of the earth.
            planetCenter.normalize();
            planetCenter.scale(SHOOT_SPEED);
            direction = planetCenter;
        }

        }






