package edu.chalmers.notenoughspace.event;

import com.jme3.scene.Spatial;
import edu.chalmers.notenoughspace.core.entity.Entity;

/**
 * Event fired when a shot collides with farmer.
 */
public class ShootCollisionEvent {

    private String shootID;
    private final int damage;

    public ShootCollisionEvent(String shootID, int damage) {
        this.shootID = shootID;
        this.damage = damage;
        System.out.println("ShootCollisionEvent fired");
    }


    public String getID() {
        return shootID;
    }


    public int getDamage() {
        return damage;
    }

}
