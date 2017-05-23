package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.Entity;

/**
 * Created by juliaortheden on 2017-05-18.
 */
public class EntityRemovedEvent {
    private Entity entity;

    public EntityRemovedEvent(Entity entity){
        this.entity = entity;
    }

    public Entity getEntity(){ return entity; }




}
