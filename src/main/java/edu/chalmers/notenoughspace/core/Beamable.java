package edu.chalmers.notenoughspace.core;

public interface Beamable {

    BeamState isInBeam();
    void setInBeam(BeamState beamState);
    int getWeight();
}
