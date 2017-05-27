package edu.chalmers.notenoughspace.core.move;

/**
 * Empty gravity strategy for objects not affected by gravity.
 */
public class ZeroGravityStrategy implements GravityStrategy {

    public void gravitate(PlanetaryInhabitant body, float tpf) {
        //Not affected by gravity.
    }

}
