package edu.chalmers.notenoughspace.core;

import javax.vecmath.Vector3f;

/**
 * Created by Vibergf on 10/05/2017.
 */
public interface PlanetaryInhabitant {

    void rotateForward(float angle);
    void rotateModel(float angle);
    Vector3f getLocalTranslation();
    Vector3f getWorldTranslation();
    float distance(PlanetaryInhabitant other);
    PlanetaryInhabitant clone();
}