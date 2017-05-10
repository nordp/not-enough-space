package edu.chalmers.notenoughspace.model;


public class Satellite {

        public final static float SATELLITE_RADIUS = 1;     //what distance?!
        private Ship ship;




        /**
         * @return the position of the spatial
         */
        //public Vector3f getPosition() { return satelliteControl.getSatellite().getWorldTranslation(); }

        /**
         * @return the distance between the satellite's vector and the ship's vector
         */
        /*public float getDistanceToShip() {
            return getPosition().distance(ship.getPosition());
        }*/

        /**
         * make the satellite explode if the ship is closer than satelliteRadius
         */
        public void explode() {

            //app.getRootNode().detachChild(satelliteNode);

            //implement some explode animation, growing sun?
            /*Spatial sun = nodeFactory.createSun().scale(0.2f, 0.2f, 0.2f);
            satelliteNode.attachChild(sun);
            */


        }

        public void explodeWhenCollision(float distanceToShip){
            while(distanceToShip <= SATELLITE_RADIUS){

            }
         }

}