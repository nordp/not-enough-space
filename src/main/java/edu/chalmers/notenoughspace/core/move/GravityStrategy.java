package edu.chalmers.notenoughspace.core.move;

/**
 * Decides how an object is affected by gravity.
 */
public interface GravityStrategy {

    void gravitate(PlanetaryInhabitant body);

}
