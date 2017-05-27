package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.Entity;

/**
 * Created by juliaortheden on 2017-05-18.
 */
public class EntityRemovedEvent {
    private Entity entity;

    public EntityRemovedEvent(Entity entity){
        this.entity = entity;
        System.out.println("EntityRemovedEvent fired: " + getClassName());
    }

    private String getClassName() {
        String classString = entity.getClass().toString();
        String[] words = classString.split("[.]");
        return words[words.length - 1];
    }

    public Entity getEntity(){ return entity; }




}
