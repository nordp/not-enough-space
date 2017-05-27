package edu.chalmers.notenoughspace.core.move;

import javax.vecmath.Vector3f;

/**
 * Interface for object moving along or above the surface of a planet.
 */
public interface PlanetaryInhabitant {

    void rotateForward(float angle);
    void rotateSideways(float angle);
    void rotateModel(float angle);
    void setDirection(Vector3f goal);
    void move(Vector3f relativeMovement);
    float distanceTo(PlanetaryInhabitant other);
    void setDistanceFromPlanetsCenter(float distance);
    float getDistanceFromPlanetsCenter();
    Vector3f getPosition();
    PlanetaryInhabitant clone();

}
