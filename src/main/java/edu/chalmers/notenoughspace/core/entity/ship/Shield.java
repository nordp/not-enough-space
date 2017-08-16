package edu.chalmers.notenoughspace.core.entity.ship;

import edu.chalmers.notenoughspace.core.entity.Entity;
import edu.chalmers.notenoughspace.core.move.GravityStrategy;
import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;
import edu.chalmers.notenoughspace.event.BeamToggleEvent;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.ShieldActiveEvent;

/**
 * Creates a shield for the ship, while active nothing can harm the health
 * of the ship.
 */
public class Shield extends Entity {

    private boolean active = true;
    private static final float ENERGY_COST = 5f;

    public Shield() {
        super(new ZeroGravityStrategy());
        Bus.getInstance().post(new EntityCreatedEvent(this));
        Bus.getInstance().register(this);
    }

    @Override
    public void onPlanetaryInhabitantAttached(){
        setActive(false);
    }

    public void setActive(boolean active) {
        if(this.active == active){
            return;
        }

        this.active = active;
        Bus.getInstance().post(new ShieldActiveEvent(active));
    }

    public boolean isActive() {
        return active;
    }

    public static float getEnergyCost() {
        return ENERGY_COST;
    }
}
