package edu.chalmers.notenoughspace.core;


import com.jme3.math.Vector3f;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;


public class Satellite implements Entity {

    //public static final float SPEED = 1;
    public static final float satelliteRadius = 1;     //what distance?!
    private Vector3f worldLocation = new Vector3f();
    private Ship ship;

    private PlanetaryInhabitant body;

    Satellite() {
//        super(parent);
        Bus.getInstance().post(new EntityCreatedEvent(this));
    }

    public void update() {

    }

    /**
     * @return the worldLocation
     */
    public Vector3f getWorldLocation() {
        return worldLocation;
    }

    public PlanetaryInhabitant getPlanetaryInhabitant() {
        return body;
    }

    public void setPlanetaryInhabitant(PlanetaryInhabitant body) {
        this.body = body;
    }
}

    /**
     * make the satellite explode if the ship is closer than satelliteRadius
     */
    /*
    public void explode() {

        //app.getRootNode().detachChild(satelliteNode);

        //implement some explode animation, growing sun?
            /*Spatial sun = nodeFactory.createSun().scale(0.2f, 0.2f, 0.2f);
            satelliteNode.attachChild(sun);



    }

    public void explodeWhenCollision(float distanceToShip){
        while(distanceToShip <= SATELLITE_RADIUS){

        }
    }
 */
