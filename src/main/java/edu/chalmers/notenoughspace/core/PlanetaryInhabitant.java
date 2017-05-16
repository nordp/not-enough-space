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
    Vector3f getLocalTranslation();
    void setDistanceToPlanetsCenter(float distance);
    Vector3f getWorldTranslation();
    float distance(PlanetaryInhabitant other);
    PlanetaryInhabitant clone();
    void move(Vector3f relativeMovement);
}
