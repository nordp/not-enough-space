package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.Entity;

/**
 * Event fired when a game entity is created.
 */
public class EntityCreatedEvent {

    private Entity entity;

    public EntityCreatedEvent(Entity entity) {
        this.entity = entity;
        System.out.println("EntityCreatedEvent fired: " + getClassName());
    }


    public Entity getEntity(){
        return entity;
    }


    private String getClassName() {
        String classString = entity.getClass().toString();
        String[] words = classString.split("[.]");
        return words[words.length - 1];
    }

}
