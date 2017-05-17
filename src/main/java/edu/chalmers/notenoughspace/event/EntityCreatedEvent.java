package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.Entity;

/**
 * Created by Phnor on 2017-05-08.
 */
public class EntityCreatedEvent {
    private Entity entity;

    public EntityCreatedEvent(Entity entity) {
        this.entity = entity;
        System.out.println("EntityCreatedEvent fired: " + getClassName());
    }

    private String getClassName() {
        String classString = entity.getClass().toString();
        String[] words = classString.split("[.]");
        return words[words.length - 1];
    }

    public Entity getEntity(){
        return entity;
    }
}
