package edu.chalmers.notenoughspace.ctrl;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.*;

public class CowControl extends AbstractControl {
    private Cow cow;
    private float ORIGINAL_SCALE;

    public CowControl(Cow cow) {
        this.cow = cow;
        ORIGINAL_SCALE = 0;
    }

    @Override
    protected void controlUpdate(float tpf) {
        JMEInhabitant ship = new JMEInhabitant(ControlUtil.getRoot(spatial).getChild("ship"));

        cow.update(ship, tpf);

        Spatial model = ((Node) spatial).getChild(0);
        if (ORIGINAL_SCALE == 0) {
            ORIGINAL_SCALE = model.getLocalScale().x;
        }

        float yDistanceToShip = Ship.ALTITUDE + Planet.PLANET_RADIUS -
                cow.getPlanetaryInhabitant().getLocalTranslation().y;

        //Adjust model size depending on height above ground (the higher the smaller):
        if (yDistanceToShip > 1f) {
            model.setLocalScale(ORIGINAL_SCALE * yDistanceToShip/Ship.ALTITUDE);
        }

        if (cow.isInBeam() == BeamState.IN_BEAM) {
            AnimControl control = model.getControl(AnimControl.class);
            AnimChannel channel = control.getChannel(0);
            if (!channel.getAnimationName().equals("jigger")) {
                channel.setAnim("jigger");
            }
            channel.setSpeed(10);
        } else {
            //model.setLocalScale(ORIGINAL_SCALE);
            AnimControl control = model.getControl(AnimControl.class);
            AnimChannel channel = control.getChannel(0);
            if (!channel.getAnimationName().equals("WalkCycle")) {
                channel.setAnim("WalkCycle");
            }

            //TODO: This animation adjusting code should not be in the control. (But where should it be??)
            if (cow.getMood() == CowMood.SCARED) {
                Node cowNode = (Node) spatial;
                cowNode.getChild(0).getControl(AnimControl.class).getChannel(0).setSpeed(10);


                if (((AudioNode)((Node) spatial).getChild("audio")).getStatus() != AudioSource.Status.Playing
                        &&
                        ((AudioNode)((Node) spatial).getChild("audio2")).getStatus() != AudioSource.Status.Playing
                        &&
                        ((AudioNode)((Node) spatial).getChild("audio3")).getStatus() != AudioSource.Status.Playing) {
                    double random = Math.random()*100;
                    if (random <= 33) {
                        ((AudioNode)((Node) spatial).getChild("audio")).play(); //plays only if not currently playing
                    } else if (random <= 66) {
                        ((AudioNode)((Node) spatial).getChild("audio2")).play(); //plays only if not currently playing
                    } else {
                        ((AudioNode)((Node) spatial).getChild("audio3")).play(); //plays only if not currently playing
                    }
                }

            } else {
                Node cowNode = (Node) spatial;
                cowNode.getChild(0).getControl(AnimControl.class).getChannel(0).setSpeed(2.5f);
            }
        }
        //Collision
        boolean colliding = ControlUtil.checkCollision(((Node) spatial).getChild(0), (ControlUtil.getRoot(spatial).getChild("beamModel")));

        if (colliding && ControlUtil.getRoot(spatial).getChild("beamModel").getCullHint() == Spatial.CullHint.Never) {
            if(cow.isInBeam() == BeamState.NOT_IN_BEAM){
                cow.enterBeam();
//                ((Node) spatial).getChild(0).rotate(0f, FastMath.DEG_TO_RAD*180f, 0f);
            }
        }else{
            if(cow.isInBeam() == BeamState.IN_BEAM){
                cow.exitBeam();
//                ((Node) spatial).getChild(0).rotate(0f, FastMath.DEG_TO_RAD*180f, 0f);
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

}
