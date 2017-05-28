package edu.chalmers.notenoughspace.core.move;

import edu.chalmers.notenoughspace.core.entity.Planet;

/**
 * Gravity strategy for objects affected by the planet's gravitation in a realistic way.
 */
public class RealisticGravityStrategy implements GravityStrategy {

    private static final float GRAVITY_CONSTANT = 9f;
    private float fallingSpeed;
    private float altitude;

    public RealisticGravityStrategy() {
        fallingSpeed = 0;
    }


    public void gravitate(PlanetaryInhabitant body, float tpf) {
        float newAltitude = body.getDistanceFromPlanetsCenter();
        if (newAltitude > altitude) {
            fallingSpeed = 0;  //Resets falling fallingSpeed if this is the beginning of a fall.
        }

        altitude = newAltitude;

        if (altitude > Planet.PLANET_RADIUS) {
            fallingSpeed += GRAVITY_CONSTANT * tpf / 8;
            body.setDistanceFromPlanetsCenter(altitude - fallingSpeed * tpf);
        } else if (altitude < Planet.PLANET_RADIUS) {
            fallingSpeed = 0;
            body.setDistanceFromPlanetsCenter(Planet.PLANET_RADIUS);
            //Object hit the ground.
        }
    }

}
