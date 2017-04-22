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
    private final static float SPEED = 0.001f;

    private Spatial shipModel;

    private float x = FastMath.rand.nextFloat() * 0.001f;
    private float y = FastMath.rand.nextFloat() * 0.001f;
    private float z = FastMath.rand.nextFloat() * 0.001f;


    public CowControl(Ship ship) {
        this.shipModel = ship.getChild(0);
    }

    @Override
    protected void controlUpdate(float tpf) {
        Spatial cowModel = ((Node) spatial).getChild(0);
        Vector3f shipPos = shipModel.getWorldTranslation();
        Vector3f cowPos = cowModel.getWorldTranslation();

        if (shipPos.distance(cowPos) < REACTION_DISTANCE) {
            Vector3f distanceVector = shipPos.subtract(cowPos);
            Vector3f projectionVector = distanceVector.project(cowPos);
            Vector3f newZAxis = distanceVector.subtract(projectionVector).negate();
            spatial.lookAt(newZAxis, cowPos);
        }

        walk();
    }

    private void walk(){
        int rand = (int)FastMath.rand.nextFloat()*100;
        if (rand < 25) {
            spatial.rotate(SPEED,0,0);
        } else if (rand < 75) {
            spatial.rotate(0,SPEED*200,0);
        } else {
            spatial.rotate(0,0,SPEED*200);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
