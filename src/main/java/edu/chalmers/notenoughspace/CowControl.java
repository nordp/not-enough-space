package edu.chalmers.notenoughspace;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class CowControl extends AbstractControl {

    private final static float REACTION_DISTANCE = 3f;
    private final static float SPEED = 0.1f;
    private static final float SPRINT_SPEED = 0.3f;
    private final static float MAX_DIR = 3;
    private final static int SPRINT_COOLDOWN = 200;
    private final static int MAX_STAMINA = 200;

    private Spatial shipModel;

    private float walkDir;
    private int stamina;
    private CowMood mood;

    public CowControl(Ship ship) {
        this.shipModel = ship.getChild(0);
        mood = CowMood.CALM;
        stamina = MAX_STAMINA;
    }

    @Override
    protected void controlUpdate(float tpf) {
        Spatial cowModel = ((Node) spatial).getChild(0);
        Vector3f shipPos = shipModel.getWorldTranslation();
        Vector3f cowPos = cowModel.getWorldTranslation();

        if (shipPos.distance(cowPos) < REACTION_DISTANCE) {
            mood = CowMood.SCARED;
        } else {
            mood = CowMood.CALM;
        }
        if (stamina < 0){
            mood = CowMood.TIRED;
            if (stamina < -SPRINT_COOLDOWN){
                stamina = MAX_STAMINA;
            }
        }


        switch (mood){
            case CALM:

                //Change walkDir twice in 100.
                float rand = FastMath.rand.nextFloat();
                if (rand*100 < 2) {
                    walkDir = FastMath.DEG_TO_RAD*(FastMath.rand.nextFloat()*MAX_DIR-MAX_DIR/2);
                }
                //Walk
                spatial.rotate(SPEED*tpf,walkDir,0);

                break;

            case SCARED:
                /*Vector3f distanceVector = shipPos.subtract(cowPos);
                Vector3f projectionVector = distanceVector.project(cowPos);
                Vector3f newZAxis = distanceVector.subtract(projectionVector).negate();
                spatial.lookAt(newZAxis, cowPos);
                spatial.rotate((FastMath.DEG_TO_RAD*-60),0,0);
                */


                //Turn left or right?
                Spatial left = ((Node)spatial.clone()).getChild(0);
                Spatial right = ((Node)spatial.clone()).getChild(0);

                left.getParent().rotate(SPEED*tpf,FastMath.DEG_TO_RAD*MAX_DIR,0);
                right.getParent().rotate(SPEED*tpf,FastMath.DEG_TO_RAD*-MAX_DIR,0);

                float sprintDir;
                if (left.getWorldTranslation().distance(shipModel.getWorldTranslation()) <
                        right.getWorldTranslation().distance(shipModel.getWorldTranslation())){
                    sprintDir = -MAX_DIR;
                } else {
                    sprintDir = MAX_DIR;
                }

                spatial.rotate(SPRINT_SPEED*tpf, FastMath.DEG_TO_RAD*sprintDir, 0);
                stamina--;
                break;

            case TIRED:
                //Walk
                spatial.rotate(SPEED/2*tpf,walkDir/2*tpf,0);
                stamina--;
                break;
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
