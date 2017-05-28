package edu.chalmers.notenoughspace.view.scene;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;

import javax.vecmath.Vector3f;

/**
 * Framework specific instance of PlanetaryInhabitant.
 */
public class JMEInhabitant implements PlanetaryInhabitant {

    private Node node;
    private Spatial model;

    public JMEInhabitant(Spatial spatial){
        if (spatial instanceof Node) {
            this.node = (Node) spatial;
        } else {
            throw new IllegalArgumentException("JMEInhabitant spatial must be a node.");
        }

        if (!node.getChildren().isEmpty()) {
            this.model = node.getChild(0);
        } else {
            throw new IllegalArgumentException("JMEInhabitant node has no child.");
        }
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

    public void rotateAroundOwnCenter(float rotX, float rotY, float rotZ) {
        model.rotate(rotX, rotY, rotZ);
    }

    public void setDirection(Vector3f goal) {
        model.lookAt(vecmathToJme(goal),
                new com.jme3.math.Vector3f(0, goal.z, -goal.y));
    }

    public void setDistanceFromPlanetsCenter(float distance) {
        model.setLocalTranslation(0, distance, 0);}

    public float getDistanceFromPlanetsCenter() {
        return model.getLocalTranslation().getY();
    }

    public Vector3f getPosition() {
        return jmeToVecmath(model.getWorldTranslation());
    }

    public float distanceTo(PlanetaryInhabitant other) {
        return model.getWorldTranslation().distance(vecmathToJme(other.getPosition()));
    }

    public PlanetaryInhabitant clone() {
        return new JMEInhabitant(node.clone());
    }

    public void move(Vector3f relativeMovement) {
        model.move(vecmathToJme(relativeMovement));
    }


    private static javax.vecmath.Vector3f jmeToVecmath(com.jme3.math.Vector3f vector) {
        return new javax.vecmath.Vector3f(vector.x, vector.y, vector.z);
    }

    private static com.jme3.math.Vector3f vecmathToJme(javax.vecmath.Vector3f vector){
        return new com.jme3.math.Vector3f(vector.x, vector.y, vector.z);
    }

}
