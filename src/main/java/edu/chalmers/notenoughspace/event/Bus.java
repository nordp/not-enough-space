package edu.chalmers.notenoughspace.event;

import com.google.common.eventbus.EventBus;

/**
 * Created by Phnor on 2017-05-08.
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
