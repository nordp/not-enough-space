package edu.chalmers.notenoughspace;

import com.jme3.math.FastMath;
        import com.jme3.math.Vector3f;
        import com.jme3.renderer.RenderManager;
        import com.jme3.renderer.ViewPort;
        import com.jme3.scene.Node;
        import com.jme3.scene.Spatial;
        import com.jme3.scene.control.AbstractControl;

public class CowControl extends AbstractControl {

    private final static float REACTION_DISTANCE = 2f;
    private final static float SPEED = 0.01f;

    private Spatial shipModel;
    private Spatial cowNode;
    private Vector3f direction = new Vector3f(0, 0, 1);
    private boolean scared = false;
    private float x = FastMath.rand.nextFloat() * 0.001f;
    private float y = FastMath.rand.nextFloat() * 0.001f;
    private float z = FastMath.rand.nextFloat() * 0.001f;



    public CowControl(Node player) {
        this.shipModel = (Node)player.getChild(0);
        //Access Geometry Spatial in Cow class.
        this.cowNode = ((Node)spatial).getChild(0);
    }

    @Override
    protected void controlUpdate(float tpf) {
        Vector3f shipPos = shipModel.getWorldTranslation();
        Vector3f cowPos = cowNode.getWorldTranslation();
        if (shipPos.distance(cowPos) < REACTION_DISTANCE) {
            //pivotNode.rotate(0, 0.01f, 0);
            Vector3f ballToShipVector = shipPos.subtract(cowPos);
            Vector3f projectionVector = ballToShipVector.project(cowPos);
            Vector3f newZAxis = ballToShipVector.subtract(projectionVector).normalizeLocal();

            cowNode.lookAt(newZAxis, cowPos);
            direction = newZAxis;
            cowNode.rotate(-SPEED, 0, 0);
            scared = false;
        } else {
            if (scared) {
                spatial.getParent().rotate(x, y, z);
            } else {
                x = -x;
                y = -y;
                z = -z;
                scared = true;
            }
        }

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
