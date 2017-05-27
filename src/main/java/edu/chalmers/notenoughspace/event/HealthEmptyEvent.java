package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.ship.Health;

/**
 * Event fired when the ship runs out of health.
 */
public class HealthEmptyEvent {


    public HealthEmptyEvent() {
        System.out.println("HealthEmptyEvent fired.");
    }

}
