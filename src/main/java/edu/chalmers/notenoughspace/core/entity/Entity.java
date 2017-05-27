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


    protected void gravitate(float tpf) {
        gravityStrategy.gravitate(body, tpf);
    }

    /**
     * To be overridden if needed. Acts as an additional constructor method for
     * logic that needs access to the PlanetaryInhabitant.
     */
    protected void onPlanetaryInhabitantAttached(){}

    public PlanetaryInhabitant getPlanetaryInhabitant() { return body;}

    public void setPlanetaryInhabitant(PlanetaryInhabitant body){
        this.body = body;
        onPlanetaryInhabitantAttached();
    }

    public String getID(){ return this.toString(); }

    public void randomizePosition(){
        if(body == null)
            return;
        body.rotateForward((float)Math.PI*2* new Random().nextFloat());
        body.rotateSideways((float)Math.PI*2* new Random().nextFloat());
    }

    public void randomizeDirection(){
        if(body == null)
            return;
        body.rotateModel((float)Math.PI*2 * new Random().nextFloat());
    }

}
