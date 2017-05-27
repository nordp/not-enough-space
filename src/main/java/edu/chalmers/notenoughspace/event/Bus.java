package edu.chalmers.notenoughspace.event;

import com.google.common.eventbus.EventBus;

/**
 * Singleton class containing the game's event bus, responsible for sending messages
 * between the classes through events. All events are fired from the core package.
 */
public class Bus {

    private static EventBus eventBus;

    public static EventBus getInstance(){
        if (eventBus == null){
            eventBus = new EventBus();
        }

        return eventBus;
    }
}
