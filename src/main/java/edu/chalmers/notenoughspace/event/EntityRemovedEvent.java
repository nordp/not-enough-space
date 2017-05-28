package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.Entity;

/**
 * Event fired when an entity is removed from the game.
 */
public class EntityRemovedEvent {

    private final Entity entity;

    public EntityRemovedEvent(Entity entity){
        this.entity = entity;
        System.out.println("EntityRemovedEvent fired: " + getClassName());
    }


    public Entity getEntity(){ return entity; }


    private String getClassName() {
        String classString = entity.getClass().toString();
        String[] words = classString.split("[.]");
        return words[words.length - 1];
    }


}
