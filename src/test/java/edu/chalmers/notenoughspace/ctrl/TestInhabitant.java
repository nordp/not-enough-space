package edu.chalmers.notenoughspace.ctrl;

import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;

/**
 * Created by Vibergf on 11/05/2017.
 */
public class TestInhabitant implements PlanetaryInhabitant {

    private Vector3f position;

    public TestInhabitant(float x, float y, float z){
        position = new Vector3f(x, y ,z);
    }

    public TestInhabitant(Vector3f position){
        this.position = position;
    }

    public void rotateForward(float angle) {
        Transform3D transform = new Transform3D();
        transform.rotX(angle);
        transform.get(position);
    }

    public void rotateSideways(float angle) {
        Transform3D transform = new Transform3D();
        transform.rotZ(angle);
        transform.get(position);
    }

    public void rotateModel(float angle) {
        Transform3D transform = new Transform3D();
        transform.rotY(angle);
        transform.get(position);
    }

    public void setDirection(Vector3f goal) {
        //TODO
    }


    public Vector3f getLocalTranslation() {
        return position;
    }

    public void setDistanceFromPlanetsCenter(float distance) {
        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(0, distance, 0));
        transform.get(position);
    }

    public float getDistanceFromPlanetsCenter() {
        return position.y;
    }

    public Vector3f getWorldTranslation() {
        return position;
    }

    public float distanceTo(PlanetaryInhabitant other) {
        Vector3f dist = other.clone().getWorldTranslation();
        dist.sub(this.getWorldTranslation());
        return dist.length();
    }

    public PlanetaryInhabitant clone() {
        return new TestInhabitant((Vector3f) position.clone());
    }

    public void move(Vector3f relativeMovement) {
        //TODO
    }
}
