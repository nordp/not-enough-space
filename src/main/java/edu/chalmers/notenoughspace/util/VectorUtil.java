package edu.chalmers.notenoughspace.util;


/**
 * Created by Phnor on 2017-05-09.
 */
public class VectorUtil {

    public static javax.vecmath.Vector3f jmeToVecmath(com.jme3.math.Vector3f vector) {
        return new javax.vecmath.Vector3f(vector.x, vector.y, vector.z);
    }

    public static com.jme3.math.Vector3f vecmathToJme(javax.vecmath.Vector3f vector){
        return new com.jme3.math.Vector3f(vector.x, vector.y, vector.z);
    }
}
