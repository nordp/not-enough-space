package edu.chalmers.notenoughspace.core.entity;

import edu.chalmers.notenoughspace.core.move.GravityStrategy;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;

import java.util.Random;

/**
 * Represents a physical entity in the 3D world where the game takes place.
 */
public abstract class Entity {

    protected PlanetaryInhabitant body;
    private GravityStrategy gravityStrategy;

    public Entity(GravityStrategy gravityStrategy) {
        this.gravityStrategy = gravityStrategy;
    }


    protected void gravitate() {
        gravityStrategy.gravitate(body);
    }

    protected void onPlanetaryInhabitantAttached(){}    //To be overridden if needed.

    public PlanetaryInhabitant getPlanetaryInhabitant() { return body;}

    public void setPlanetaryInhabitant(PlanetaryInhabitant body){
        this.body = body;
        onPlanetaryInhabitantAttached();
    }

    public String getID(){ return this.toString(); }

    public static void randomizePosition(PlanetaryInhabitant body){
        body.rotateForward((float)Math.PI*2* new Random().nextFloat());
        body.rotateSideways((float)Math.PI*2* new Random().nextFloat());
    }

    public static void randomizeDirection(PlanetaryInhabitant body){
        body.rotateModel((float)Math.PI*2 * new Random().nextFloat());
    }

}
