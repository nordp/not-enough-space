package edu.chalmers.notenoughspace;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

        import com.jme3.math.ColorRGBA;
        import com.jme3.math.FastMath;
        import com.jme3.math.Vector3f;
        import com.jme3.renderer.RenderManager;
        import com.jme3.renderer.ViewPort;
        import com.jme3.scene.Geometry;
        import com.jme3.scene.Node;
        import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Sparven
 */
public class BallControl extends AbstractControl {

    private final static float REACTION_DISTANCE = 2f;
    private final static float SPEED = 0.01f;

    private Geometry player;
    private Vector3f direction = new Vector3f(0, 0, 1);
    private Game app;
    private Geometry line1;
    private Geometry line2;
    private Geometry line3;
    private float x = FastMath.rand.nextFloat() * 0.001f;
    private float y = FastMath.rand.nextFloat() * 0.001f;
    private float z = FastMath.rand.nextFloat() * 0.001f;

    public BallControl(Geometry player, Game app) {
        this.player = player;
        this.app = app;

//        line1 = app.makeLine(Vector3f.ZERO, Vector3f.UNIT_X, ColorRGBA.Cyan);
//        line2 = app.makeLine(Vector3f.ZERO, Vector3f.UNIT_Y, ColorRGBA.Cyan);
//        line3 = app.makeLine(Vector3f.ZERO, Vector3f.UNIT_Z, ColorRGBA.Cyan);
//
//        app.add(line1);
//        app.add(line2);
//        app.add(line3);
    }



    @Override
    protected void controlUpdate(float tpf) {
        Vector3f playerPos = player.getWorldTranslation();
        Vector3f ballPos = spatial.getWorldTranslation();
        if (playerPos.distance(ballPos) < REACTION_DISTANCE) {
            Node pivotNode = spatial.getParent();
            //pivotNode.rotate(0, 0.01f, 0);
            Vector3f ballToShipVector = playerPos.subtract(ballPos);
            Vector3f projectionVector = ballToShipVector.project(ballPos);
            Vector3f newZAxis = ballToShipVector.subtract(projectionVector)
                    .normalizeLocal();


            Vector3f up = ballPos.normalize();


            //app.remove(line1);
            //app.remove(line2);
            //app.remove(line3);
            //line1 = app.makeLine(Vector3f.ZERO, up.mult(5), ColorRGBA.Cyan);
//            line2 = app.makeLine(Vector3f.ZERO, direction, ColorRGBA.Green);
//            line3 = app.makeLine(Vector3f.ZERO, newZAxis, ColorRGBA.White);
//            app.add(line1);
//            app.add(line2);
//            app.add(line3);


            float angleToRotate = direction.angleBetween(newZAxis);
            //pivotNode.rotateUpTo(playerPos.subtract(ballPos));

            Vector3f v = direction.cross(newZAxis);
            if (v.angleBetween(ballPos) > 1) {
                angleToRotate = -angleToRotate;
            }
            //System.out.println(v.angleBetween(ballPos) * FastMath.RAD_TO_DEG);

            pivotNode.lookAt(newZAxis, up);
            //spatial.move(newZAxis);

            //pivotNode.rotate(0, angleToRotate, 0);
            direction = newZAxis;
            pivotNode.rotate(-SPEED, 0, 0);



            //System.out.println(angleToRotate*FastMath.RAD_TO_DEG);
            changedDirection = false;


        } else {
            if (changedDirection) {
                spatial.getParent().rotate(x, y, z);
            } else {
                x = -x;
                y = -y;
                z = -z;
                changedDirection = true;
            }
        }

    }

    private boolean changedDirection = false;

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
