package edu.chalmers.notenoughspace.event;

/**
 * Event fired when a hayfork collides with the ship.
 */
public class HayforkCollisionEvent {

    private final String hayForkID;
    private final int damage;

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
