package edu.chalmers.notenoughspace.ctrl;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.Cow;
import edu.chalmers.notenoughspace.util.NodeUtil;

import static edu.chalmers.notenoughspace.core.Cow.*;

public class CowControl extends AbstractControl {
    private Cow cow;

    public CowControl(Cow cow) {
        this.cow = cow;
    }

    @Override
    protected void controlUpdate(float tpf) {
        /*Spatial shipModel = NodeUtil.getRoot(spatial).getChild("ship");
        Spatial cowModel = ((Node) spatial).getChild(0);
        Vector3f shipPos = shipModel.getWorldTranslation();
        Vector3f cowPos = cowModel.getWorldTranslation();

        cow.updateState(shipPos.distance(cowPos));


        switch (cow.getMood()){
            case CALM:

                //Change walkDir twice in 100.
                float rand = FastMath.rand.nextFloat();
                if (rand*100 < 2) {
                    cow.setDirection(FastMath.DEG_TO_RAD*(FastMath.rand.nextFloat()*MAX_DIR-MAX_DIR/2));
                }
                //Walk
                spatial.rotate(cow.getSpeed()*tpf,cow.getDirection(),0);

                break;

            case SCARED:
                /*Vector3f distanceVector = shipPos.subtract(cowPos);
                Vector3f projectionVector = distanceVector.project(cowPos);
                Vector3f newZAxis = distanceVector.subtract(projectionVector).negate();
                spatial.lookAt(newZAxis, cowPos);
                //spatial.rotate((FastMath.DEG_TO_RAD*-60),0,0);



                //Turn left or right?
                Spatial left = ((Node)spatial.clone()).getChild(0);
                Spatial right = ((Node)spatial.clone()).getChild(0);

                left.getParent().rotate(cow.getSpeed()*tpf,FastMath.DEG_TO_RAD*MAX_DIR,0);
                right.getParent().rotate(cow.getSpeed()*tpf,FastMath.DEG_TO_RAD*-MAX_DIR,0);

                float sprintDir;
                if (left.getWorldTranslation().distance(shipModel.getWorldTranslation()) <
                        right.getWorldTranslation().distance(shipModel.getWorldTranslation())){
                    sprintDir = -MAX_DIR;
                } else {
                    sprintDir = MAX_DIR;
                }

                spatial.rotate(SPRINT_SPEED*tpf, FastMath.DEG_TO_RAD*sprintDir, 0);
                cow.reduceStamina();
                break;

            case TIRED:
                //Walk
                spatial.rotate(cow.getSpeed()/2*tpf,cow.getDirection()/2*tpf,0);
                cow.reduceStamina();
                break;
        }
        */
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
