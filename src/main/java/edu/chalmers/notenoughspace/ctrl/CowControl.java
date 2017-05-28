package edu.chalmers.notenoughspace.ctrl;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import com.jme3.effect.ParticleEmitter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.entity.beamable.Cow;
import edu.chalmers.notenoughspace.core.entity.beamable.CowMood;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;

/**
 * Control responsible for telling the cow when to update and when and how cow related
 * animations and sounds should be played.
 */
public class CowControl extends DetachableControl {

    private float ORIGINAL_SCALE;

    private final Cow cow;
    private boolean sweatEnabled;

    public CowControl(Cow cow) {
        this.cow = cow;
        ORIGINAL_SCALE = 0;
    }


    @Override
    protected void controlUpdate(float tpf) {
        setOriginalScale(); //TODO: What do we do about this? Create an onAttached method?

        PlanetaryInhabitant ship = ShipControl.getShip();
        boolean isTired = cow.getMood() == CowMood.TIRED;

        cow.update(ship, tpf);
        setSweatEffect(isTired);
        adjustSizeRelativeToAltitude(); //The higher the smaller.
        setAnimation();
        checkCollisionWithBeam();
    }


    private void setOriginalScale() {
        Spatial model = ((Node) spatial).getChild(0);
        if (ORIGINAL_SCALE == 0) {
            ORIGINAL_SCALE = model.getLocalScale().x;   //Or y or z, should always be scaled by same value
                                                        //in all directions.
        }
    }

    private void setSweatEffect(boolean isEnabled){
        if (sweatEnabled == isEnabled) {
            return;
        }

        sweatEnabled = isEnabled;
        if (sweatEnabled) {
            ((ParticleEmitter) ((Node)spatial).getChild("sweat")).setEnabled(true);
        } else {
            ((ParticleEmitter) ((Node)spatial).getChild("sweat")).setEnabled(false);
            ((ParticleEmitter) ((Node)spatial).getChild("sweat")).killAllParticles();
        }
    }

    private void adjustSizeRelativeToAltitude() {
        float cowAltitude = cow.getPlanetaryInhabitant().getDistanceFromPlanetsCenter();
        float yDistanceToShip = (Ship.ALTITUDE + Planet.PLANET_RADIUS) - cowAltitude;

        if (yDistanceToShip > 1f) {
            float newScale = ORIGINAL_SCALE * yDistanceToShip/Ship.ALTITUDE;
            getModel().setLocalScale(newScale);
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
        AudioNode mooSound1 = (AudioNode)((Node) spatial).getChild("audio");
        AudioNode mooSound2 = (AudioNode)((Node) spatial).getChild("audio2");
        AudioNode mooSound3 = (AudioNode)((Node) spatial).getChild("audio3");

        return mooSound1.getStatus() == AudioSource.Status.Playing ||
                mooSound2.getStatus() == AudioSource.Status.Playing ||
                mooSound3.getStatus() == AudioSource.Status.Playing;
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
        if (cow.isInBeam()) {
            setJiggerAnimation();
        } else {
            setWalkAnimation();
            setAnimationSpeed();    //Depending on mood.
        }
    }

    private void setJiggerAnimation() {
        AnimControl control = getModel().getControl(AnimControl.class);
        AnimChannel channel = control.getChannel(0);

        if (channel.getAnimationName() == null || !channel.getAnimationName().equals("jigger")) {
            channel.setAnim("jigger");
        }
        channel.setSpeed(10);
    }

    private void setWalkAnimation() {
        AnimControl control = getModel().getControl(AnimControl.class);
        AnimChannel channel = control.getChannel(0);

        if (channel.getAnimationName() == null || !channel.getAnimationName().equals("WalkCycle")) {
            channel.setAnim("WalkCycle");
        }
    }

    private void setAnimationSpeed() {
        AnimChannel animationChannel = getModel().getControl(AnimControl.class).getChannel(0);

        if (cow.getMood() == CowMood.SCARED) {
            animationChannel.setSpeed(10);
            moo();
        } else {
            animationChannel.setSpeed(2.5f);
        }
    }

    private void checkCollisionWithBeam() {
        Spatial beamModel = ControlUtil.getRoot(spatial).getChild("beamModel");

        boolean colliding = ControlUtil.checkCollision(getModel(), beamModel);

        //This is bad, we shouldn't check the view for logic. It's much easier than trying to look up the Beam Entity though.
        boolean beamVisible = beamModel.getCullHint() == Spatial.CullHint.Never;

        if (colliding && beamVisible) {
            if (!cow.isInBeam()) {
                cow.enterBeam();
            }
        } else {
            if (cow.isInBeam()) {
                cow.exitBeam();
            }
        }
    }

    private Spatial getModel() {
        return ((Node) spatial).getChild("model");
    }

}
