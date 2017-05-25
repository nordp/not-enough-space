package edu.chalmers.notenoughspace.core.entity;

import edu.chalmers.notenoughspace.core.move.GravityStrategy;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;

import java.util.Random;

/**
 * Created by Vibergf on 08/05/2017.
 */
public abstract class Entity {

    protected PlanetaryInhabitant body;
    protected GravityStrategy gravityStrategy;

    public Entity(GravityStrategy gravityStrategy) {
        this.gravityStrategy = gravityStrategy;
    }

    public PlanetaryInhabitant getPlanetaryInhabitant() { return body;}

    public void setPlanetaryInhabitant(PlanetaryInhabitant body){
        this.body = body;
        onPlanetaryInhabitantAttached();
    }

    protected void onPlanetaryInhabitantAttached(){}

    public String getID(){ return this.toString(); }

    protected void gravitate() {
        gravityStrategy.gravitate(body);
    }

    protected static void randomizePosition(PlanetaryInhabitant body){
        body.rotateForward((float)Math.PI*2* new Random().nextFloat());
        body.rotateSideways((float)Math.PI*2* new Random().nextFloat());
    }

    protected static void randomizeDirection(PlanetaryInhabitant body){
        body.rotateModel((float)Math.PI*2 * new Random().nextFloat());
    }
}
