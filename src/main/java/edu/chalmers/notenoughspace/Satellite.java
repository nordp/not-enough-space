
    package edu.chalmers.notenoughspace;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.*;
import com.jme3.scene.shape.Box;

    /**
     * A model of space ship able to be navigated around a planet's surface.
     * The Ship object itself is a node (functioning as a pivot node) containing a
     * visual space ship model.
     */
    public class Satellite extends Node {

        /** The ship's private spot light, lighting up the surface beneath it. */
        private SpotLight spotLight;
        private float height;
        private AssetManager assetManager;



        /**
         * Creates a steerable ship model and attaches it to the pivot
         * node that is this object.
         * NOTE: Does not add the third person view camera. This is done
         * in its own separate method.
         * @param assetManager Used to load the model and texture for the ship.
         */

        public Satellite(AssetManager assetManager, float height){
            this.height = height;
            this.assetManager = assetManager;

            createSatelliteModel(assetManager);

        }

        /**
         * Creates a visual model of the ship (named "ship") and attaches it
         * to the center of the Ship object (i.e. this node).
         * @param assetManager
         */
        private void createSatelliteModel(AssetManager assetManager) {
            //Spatial shipModel = new Geometry("ship", createShipMesh()); //Temporary, should be a real model.
            Spatial shipModel = assetManager.loadModel("Models/ufo.obj");
            //shipModel.setMaterial( createShipMaterial(assetManager) );  //No material needed when real model added.
            shipModel.setMaterial(assetManager.loadMaterial("Materials/UfoMaterial.j3m"));
            shipModel.setName("ship");  //Important, used in many places for accessing the ship model.
            shipModel.scale(0.02f, 0.02f, 0.02f);
            attachChild(shipModel);
        }





        /**
         * Initializes a spotlight directed downwards from the
         * bottom of the ship. NOTE: Since a light source only affects the
         * nodes BELOW itself in the hierarchy it must be attached to the
         * root node of the game in order to light up the planet, cows, etc.
         * Therefore it always has to be updated with the coordinates of
         * the ship (which is currently done in the inputManager when the
         * player moves the ship).
         */
        private void initSpotLight() {
            spotLight = new SpotLight();
            spotLight.setSpotRange(10);
            spotLight.setSpotOuterAngle(45 * FastMath.DEG_TO_RAD);
            spotLight.setSpotInnerAngle(5 * FastMath.DEG_TO_RAD);
            spotLight.setPosition(this.getChild("ship").getWorldTranslation());
            spotLight.setDirection(this.getChild("ship").getWorldTranslation().mult(-1));
            spotLight.setName("shipSpotLight");
        }

        /** Returns the ship's spotlight. */
        public SpotLight getSpotLight() {
            return this.spotLight;
        }



        /////////// MOVEMENTS BELOW //////////////

        //todo: create automatic movement around the planet

        private edu.chalmers.notenoughspace.Satellite getMe() {
            return this;
        }

    }


