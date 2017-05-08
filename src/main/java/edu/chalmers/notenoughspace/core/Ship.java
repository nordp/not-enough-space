package edu.chalmers.notenoughspace.core;

/**
 * Created by Vibergf on 25/04/2017.
 */
public class Ship extends Spatial3D {

    public static final float SPEED = 1;
    public static final float ROTATION_SPEED = 2;

    private int energy;
    public Beam beam;

    public Ship(Spatial3D parent){
        super(parent);
        energy = 100;
        beam = new Beam(this);
        detachChild(beam);

        //beamNode.setLocalTranslation(beamNode.getLocalTranslation().add(this.getChild("ship").getLocalTranslation()));
    }

    public void update() {

    }

    public boolean isBeamActive() {
        return beam.beamActive;
    }

    public void setBeamActive(boolean beamActive) {
        beam.beamActive = beamActive;
    }

    private class Beam extends Spatial3D{
        private boolean beamActive = false;

        public Beam(Spatial3D parent) {
            super(parent);
        }

        public void update() {

        }

        public void setActive(boolean active) {
            if(beamActive = active){
                return;
            }

            beamActive = active;

            if(active){
                attachChild(beam);
            }else{
                detachChild(beam);
            }
        }
    }
}
