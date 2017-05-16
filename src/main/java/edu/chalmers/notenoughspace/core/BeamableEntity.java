package edu.chalmers.notenoughspace.core;

public interface BeamableEntity extends Entity{

    BeamState isInBeam();
    void enterBeam();
    void exitBeam();
    float getWeight();
    float getPoints();
}
