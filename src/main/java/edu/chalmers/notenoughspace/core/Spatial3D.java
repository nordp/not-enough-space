package edu.chalmers.notenoughspace.core;

import edu.chalmers.notenoughspace.event.AttachedEvent;
import edu.chalmers.notenoughspace.event.Bus;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

/**
 * Created by Vibergf on 08/05/2017.
 */
public abstract class Spatial3D {

    private Transform3D transformation;
    private Vector3f translation;

    protected ArrayList<Spatial3D> children;

    public Spatial3D(Spatial3D parent){
        transformation = new Transform3D();
        transformation = new Transform3D();
        children = new ArrayList<Spatial3D>();

        Bus.getInstance().post(new AttachedEvent(parent, this, true));
    }

    public Vector3f getTranslation() {
        return translation;
    }

//    public void setPosition(Vector3f position) {
//        this.position = position;
//    }

    public void attachChild(Spatial3D child){
        children.add(child);
        Bus.getInstance().post(new AttachedEvent(this, child, true));
    }

    public void detachChild(Spatial3D child){
        children.remove(child);
        Bus.getInstance().post(new AttachedEvent(this, child, false));
    }

    public abstract void update();
}
