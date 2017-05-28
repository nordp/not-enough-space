package edu.chalmers.notenoughspace.ctrl;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.entity.beamable.Junk;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;

/**
 * Control responsible for telling the junk when to update and
 * for notifying it when it is colliding with the beam.
 */
public class JunkControl extends DetachableControl {
    
    private float ORIGINAL_SCALE;
    
    private final Junk junk;

    public JunkControl(Junk junk){
        this.junk = junk;
    }


    protected void controlUpdate(float tpf) {
        setOriginalScale(); //TODO: What do we do about this? Create an onAttached method?

        adjustSizeRelativeToAltitude(); //The higher the smaller.
        checkCollisionWithBeam(tpf);
    }


    private void setOriginalScale() {
        Spatial model = ((Node) spatial).getChild(0);
        if (ORIGINAL_SCALE == 0) {
            ORIGINAL_SCALE = model.getLocalScale().x;   //Or y or z, should always be scaled by same value
            //in all directions.
        }
    }

    private void adjustSizeRelativeToAltitude() {
        float junkAltitude = junk.getPlanetaryInhabitant().getDistanceFromPlanetsCenter();
        float yDistanceToShip = (Ship.ALTITUDE + Planet.PLANET_RADIUS) - junkAltitude;

        if (yDistanceToShip > 1f) {
            float newScale = ORIGINAL_SCALE * yDistanceToShip/Ship.ALTITUDE;
            getModel().setLocalScale(newScale);
        }
    }

    private void checkCollisionWithBeam(float tpf) {
        Spatial beamModel = ControlUtil.getRoot(spatial).getChild("beamModel");

        boolean colliding = ControlUtil.checkCollision(getModel(), beamModel);

        //This is bad, we shouldn't check the view for logic. It's much easier than trying to look up the Beam Entity though.
        boolean beamVisible = beamModel.getCullHint() == Spatial.CullHint.Never;

        if (colliding && beamVisible) {
            if(!junk.isInBeam()){
                junk.enterBeam();
            }
        } else {
            if (junk.isInBeam()) {
                junk.exitBeam();
            }

            junk.update(tpf); //Gravitates the junk.
        }
    }

    private Spatial getModel() {
        return ((Node) spatial).getChild(0);
    }

}
