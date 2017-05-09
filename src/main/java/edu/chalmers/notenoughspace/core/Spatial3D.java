package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.AttachedEvent;
import edu.chalmers.notenoughspace.event.Bus;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

/**
 * Created by Vibergf on 08/05/2017.
 */
public interface Spatial3D {

//    public Spatial3D(Spatial3D parent){
//        fireEvent(parent);
//    }

    void fireEvent(Spatial3D parent);

//    void update();
}
