package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.Spatial3D;

/**
 * Created by Phnor on 2017-05-08.
 */
public class AttachedEvent {
    public Spatial3D parent;
    public Spatial3D child;
    public boolean attached;

    public AttachedEvent(Spatial3D parent, Spatial3D child, boolean attached) {
        this.parent = parent;
        this.child = child;
        this.attached = attached;
        System.out.println("Event fired");
    }
}
