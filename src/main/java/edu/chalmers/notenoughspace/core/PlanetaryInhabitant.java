package edu.chalmers.notenoughspace.core;

import javax.vecmath.Vector3f;

/**
 * Created by Vibergf on 10/05/2017.
 */
public interface PlanetaryInhabitant {

    void rotateForward(float angle);
    void rotateSideways(float angle);
    void rotateModel(float angle);
    void setDirection(Vector3f goal);
    void move(Vector3f relativeMovement);
    float distance(PlanetaryInhabitant other);
    void setDistanceToPlanetsCenter(float distance);
    float getDistanceToPlanetsCenter();
    Vector3f getLocalTranslation();
    Vector3f getWorldTranslation();
    PlanetaryInhabitant clone();
}
