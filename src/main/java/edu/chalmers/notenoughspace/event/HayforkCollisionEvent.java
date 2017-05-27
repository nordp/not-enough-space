package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.entity.enemy.Hayfork;

/**
 * Event fired when a hayfork collides with the ship.
 */
public class HayforkCollisionEvent {

    private String hayForkID;
    private int damage;

    public HayforkCollisionEvent(String hayForkID, int damage) {
        this.hayForkID = hayForkID;
        this.damage = damage;
        System.out.println("HayforkCollisionEvent fired");
    }


    public String getHayForkID() {
        return hayForkID;
    }


    public int getDamage() {
        return damage;
    }

}
