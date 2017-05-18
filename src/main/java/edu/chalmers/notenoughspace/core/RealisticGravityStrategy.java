package edu.chalmers.notenoughspace.core;

/**
 * Created by Sparven on 2017-05-18.
 */
public class RealisticGravityStrategy extends GravityStrategy {
    private static final float GRAVITY_CONSTANT = 0.0003f;
    private float speed;
    private float objectsHeight;

    public RealisticGravityStrategy() {
        speed = 0;
    }

    public void gravitate(PlanetaryInhabitant body) {
        if (body.getLocalTranslation().y > objectsHeight) {
            //Resets falling speed if this is the beginning of a fall
            speed = 0;
        }

        objectsHeight = body.getLocalTranslation().y;

        if (objectsHeight > Planet.PLANET_RADIUS) {
            speed += GRAVITY_CONSTANT;
            body.setDistanceToPlanetsCenter(objectsHeight - speed);
        }
        if (objectsHeight < Planet.PLANET_RADIUS) {
            //Resets falling speed and position if body hit the ground
            speed = 0;
            body.setDistanceToPlanetsCenter(Planet.PLANET_RADIUS);
        }
    }
}
