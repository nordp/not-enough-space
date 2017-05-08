package edu.chalmers.notenoughspace.core;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

/**
 * Created by Vibergf on 08/05/2017.
 */
public abstract class Spatial3D {

    private Transform3D localTranslation;
    private Transform3D worldTranslation;

    private ArrayList<Spatial3D> children;

    public Spatial3D(Spatial3D parent){
        localTranslation = new Transform3D();
        worldTranslation = new Transform3D();
        children = new ArrayList<Spatial3D>();
    }

//    public Transform3D getWorldTranslation() {
//        return position;
//    }
//
//    public void setPosition(Vector3f position) {
//        this.position = position;
//    }

    public void attachChild(Spatial3D child){
        children.add(child);
    }

    public void detachChild(Spatial3D child){
        children.remove(child);
    }
}
