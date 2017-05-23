package edu.chalmers.notenoughspace.ctrl;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;

import javax.vecmath.Vector3f;

/**
 * Created by Vibergf on 10/05/2017.
 */
public class JMEInhabitant implements PlanetaryInhabitant {

    private Node node;
    private Spatial model;

    public JMEInhabitant(Spatial spatial){
        if(spatial instanceof Node)
            this.node = (Node) spatial;
        else
            System.out.println("JMEInhabitant spatial must be a node!"); //Temporary error message?

        if(!node.getChildren().isEmpty())
            this.model = node.getChild(0);
        else
            System.out.println("JMEInhabitant node has no child!"); //^
    }

    public void rotateForward(float angle) {
        node.rotate(angle, 0f, 0f);
    }

    public void rotateSideways(float angle) {
        node.rotate(0f, 0f, angle);
    }

    public void rotateModel(float angle){
        node.rotate(0f, angle, 0f);
    }

    public void setDirection(Vector3f goal) {
        model.lookAt(vecmathToJme(goal),
                new com.jme3.math.Vector3f(0, goal.z, -goal.y));
    }

    public Vector3f getLocalTranslation() {
        return jmeToVecmath(model.getLocalTranslation());
    }

    public void setDistanceToPlanetsCenter(float distance) {
        model.setLocalTranslation(0, distance, 0);}

    public float getDistanceToPlanetsCenter() {
        return model.getLocalTranslation().getY();
    }

    public Vector3f getWorldTranslation() {
        return jmeToVecmath(model.getWorldTranslation());
    }

    public float distance(PlanetaryInhabitant other) {
        return model.getWorldTranslation().distance(vecmathToJme(other.getWorldTranslation()));
    }

    public PlanetaryInhabitant clone() {
        return new JMEInhabitant(node.clone());
    }

    public void move(Vector3f relativeMovement) {
        model.move(vecmathToJme(relativeMovement));
    }


    //Util Methods
    private static javax.vecmath.Vector3f jmeToVecmath(com.jme3.math.Vector3f vector) {
        return new javax.vecmath.Vector3f(vector.x, vector.y, vector.z);
    }

    private static com.jme3.math.Vector3f vecmathToJme(javax.vecmath.Vector3f vector){
        return new com.jme3.math.Vector3f(vector.x, vector.y, vector.z);
    }
}
