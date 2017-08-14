package edu.chalmers.notenoughspace.ctrl;

import com.google.common.eventbus.Subscribe;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.entity.enemy.Farmer;
import edu.chalmers.notenoughspace.core.entity.ship.ShootWeapon;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;
import edu.chalmers.notenoughspace.event.*;

import java.util.List;

/**
 * Control responsible for telling the shoot weapon when to update and
 * for notifying it when it collides with the farmer.
 */

public class ShootControl extends DetachableControl {

    private ShootWeapon shootWeapon;

    public ShootControl(ShootWeapon shootWeapon){
        this.shootWeapon = shootWeapon;
        Bus.getInstance().register(this);
    }

    protected void controlUpdate(float tpf) {

        shootWeapon.update(tpf);
        checkCollisionWithFarmer();
        checkCollisionWithPlanet();
    }

    private void checkCollisionWithFarmer() {
        Spatial farmerModel =  ControlUtil.getRoot(spatial).getChild("farmerModel");
        boolean colliding = ControlUtil.checkCollision(getModel(), farmerModel);

        if (colliding) {
            shootWeapon.hitSomething();
        }
    }

    private void checkCollisionWithPlanet() {
        Spatial planetModel =  ControlUtil.getRoot(spatial).getChild("planetModel");
        boolean colliding = ControlUtil.checkCollision(getModel(), planetModel);

        if (colliding) {
            spatial.setCullHint(Spatial.CullHint.Always);
        }
    }

    private Spatial getModel() {
        return ((Node) spatial).getChild(0);
    }


    @Override
    public void onDetach() {
        Bus.getInstance().unregister(this);
    }


}