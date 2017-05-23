package edu.chalmers.notenoughspace.core.entity;

import edu.chalmers.notenoughspace.core.move.GravityStrategy;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;

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

    public void setPlanetaryInhabitant(PlanetaryInhabitant body){ this.body = body; }

    public String getID(){ return this.toString(); }

    protected void gravitate() {
        gravityStrategy.gravitate(body);
    }
}
