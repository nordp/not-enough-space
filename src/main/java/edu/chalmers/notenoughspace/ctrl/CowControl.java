package edu.chalmers.notenoughspace.ctrl;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import com.jme3.effect.ParticleEmitter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.core.entity.beamable.Cow;
import edu.chalmers.notenoughspace.core.entity.beamable.CowMood;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.entity.beamable.BeamState;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;

public class CowControl extends DetachableControl {
    private Cow cow;
    private float ORIGINAL_SCALE;
    private boolean sweatEnabled;

    public CowControl(Cow cow) {
        this.cow = cow;
        ORIGINAL_SCALE = 0;
    }

    @Override
    protected void controlUpdate(float tpf) {
        JMEInhabitant ship = new JMEInhabitant(ControlUtil.getRoot(spatial).getChild("ship"));
        cow.update(ship, tpf);

        //Sets original scale if not already set. Should really be in some initialize method
        //but there is no such method, right?
        setORIGINAL_SCALE();

        setSweatEffect(cow.getMood() == CowMood.TIRED);
        adjustSizeRelativeToAltitude();
        setAnimation();
        checkCollision();
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    private void setSweatEffect(boolean enabled){
        if(sweatEnabled == enabled)
            return;
        sweatEnabled = enabled;
        if(sweatEnabled){
            ((ParticleEmitter) ((Node)spatial).getChild("sweat")).setEnabled(true);
        }else{
            ((ParticleEmitter) ((Node)spatial).getChild("sweat")).setEnabled(false);
            ((ParticleEmitter) ((Node)spatial).getChild("sweat")).killAllParticles();
        }
    }

    private void setORIGINAL_SCALE() {
        Spatial model = ((Node) spatial).getChild(0);
        if (ORIGINAL_SCALE == 0) {
            ORIGINAL_SCALE = model.getLocalScale().x;
        }
    }

    private void adjustSizeRelativeToAltitude() {
        Spatial model = ((Node) spatial).getChild(0);
        float yDistanceToShip = Ship.ALTITUDE + Planet.PLANET_RADIUS -
                cow.getPlanetaryInhabitant().getLocalTranslation().y;

        //Adjust model size depending on height above ground (the higher the smaller):
        if (yDistanceToShip > 1f) {
            model.setLocalScale(ORIGINAL_SCALE * yDistanceToShip/Ship.ALTITUDE);
        }
    }

    private void moo() {
        if (!isMooing()) {
            double random = Math.random()*100;
            if (random <= 33) {
                playMoo1();
            } else if (random <= 66) {
                playMoo2();
            } else {
                playMoo3();
            }
        }
    }

    private boolean isMooing() {
        return !(((AudioNode)((Node) spatial).getChild("audio")).getStatus() != AudioSource.Status.Playing &&
                ((AudioNode)((Node) spatial).getChild("audio2")).getStatus() != AudioSource.Status.Playing &&
                ((AudioNode)((Node) spatial).getChild("audio3")).getStatus() != AudioSource.Status.Playing);
    }

    private void playMoo1() {
        ((AudioNode)((Node) spatial).getChild("audio")).play();
    }

    private void playMoo2() {
        ((AudioNode)((Node) spatial).getChild("audio2")).play();
    }

    private void playMoo3() {
        ((AudioNode)((Node) spatial).getChild("audio3")).play();
    }

    private void setAnimation() {
        if (cow.isInBeam() == BeamState.IN_BEAM) {
            setJiggerAnimation();
        } else {
            setWalkAnimation();
            setAnimationSpeed();
        }
    }

    private void setJiggerAnimation() {
        Spatial model = ((Node) spatial).getChild(0);
        AnimControl control = model.getControl(AnimControl.class);
        AnimChannel channel = control.getChannel(0);
        if (!channel.getAnimationName().equals("jigger")) {
            channel.setAnim("jigger");
        }
        channel.setSpeed(10);
    }

    private void setWalkAnimation() {
        Spatial model = ((Node) spatial).getChild(0);
        AnimControl control = model.getControl(AnimControl.class);
        AnimChannel channel = control.getChannel(0);
        if (!channel.getAnimationName().equals("WalkCycle")) {
            channel.setAnim("WalkCycle");
        }
    }

    private void setAnimationSpeed() {
        Node cowNode = (Node) spatial;
        if (cow.getMood() == CowMood.SCARED) {
            cowNode.getChild(0).getControl(AnimControl.class).getChannel(0).setSpeed(10);
            moo();
        } else {
            cowNode.getChild(0).getControl(AnimControl.class).getChannel(0).setSpeed(2.5f);
        }
    }

    private void checkCollision() {
        boolean colliding = ControlUtil.checkCollision(
                ((Node) spatial).getChild("model"), (ControlUtil.getRoot(spatial).getChild("beamModel")));

        if (colliding && ControlUtil.getRoot(spatial).getChild("beamModel").getCullHint() == Spatial.CullHint.Never) {
            if (cow.isInBeam() == BeamState.NOT_IN_BEAM) {
                cow.enterBeam();
            }
        } else {
            if (cow.isInBeam() == BeamState.IN_BEAM) {
                cow.exitBeam();
            }
        }

    }

}
