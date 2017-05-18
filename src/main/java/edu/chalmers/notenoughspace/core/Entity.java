package edu.chalmers.notenoughspace.core;

/**
 * Created by Vibergf on 08/05/2017.
 */
public abstract class Entity {

    protected PlanetaryInhabitant body;

    public PlanetaryInhabitant getPlanetaryInhabitant() { return body;}

    public void setPlanetaryInhabitant(PlanetaryInhabitant body){ this.body = body; }

    public String getID(){ return this.toString(); }

}
