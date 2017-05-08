package edu.chalmers.notenoughspace.core;


import com.jme3.math.Vector3f;


    public class Satellite {

        //public static final float SPEED = 1;
        public static final float satelliteRadius = 1;     //what distance?!
        private Vector3f worldLocation = new Vector3f();
        private Ship ship;

        Satellite() {

        }

        /**
         * @return the worldLocation
         */
        public Vector3f getWorldLocation() {
            return worldLocation;
        }

    }

    /**
     * make the satellite explode if the ship is closer than satelliteRadius
     */
   /* public void explode(){
        if(getDistance()<= satelliteRadius) {
            Sphere sphere = new Sphere();
            app.getRootNode().attachChild();
            sphere.setMaterial(ModelManager.loadMaterial("sun"));
            sphere.move(-20, 0, 10);
            sphere.setLocalTranslation(-100, 0, 0);
            sphere.rotate(0, 0, FastMath.HALF_PI);

        }

        }
    }

}
 */
