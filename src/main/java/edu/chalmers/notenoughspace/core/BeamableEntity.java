package edu.chalmers.notenoughspace.core;

public interface BeamableEntity extends Entity{

    BeamState isInBeam();
    void setInBeam(BeamState beamState);
    int getWeight();
}
