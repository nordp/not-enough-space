
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

    public class Satellite extends Node{

        private float height;
        private AssetManager assetManager;


        public Satellite(float height, AssetManager assetManager){
            this.height = height;
            this.assetManager = assetManager;
            //this.satellite = satellite;

        }

        public Spatial createSatellite(AssetManager assetManager) {
            Node satellite = new Node();
            Spatial satelliteModel = assetManager.loadModel("Models/ufo.obj");
            satelliteModel.setLocalTranslation(0,height,0);
            satelliteModel.setName("satellite");
            satelliteModel.setMaterial(assetManager.loadMaterial("Materials/UfoMaterial.j3m"));
            satelliteModel.scale(0.01f, 0.01f, 0.01f);
            satellite.attachChild(satelliteModel);
            satellite.addControl(new SatelliteControl((Node) satellite, new Satellite(height, assetManager)));

            return satelliteModel;
        }



        private edu.chalmers.notenoughspace.Satellite getMe() {
            return this;
        }

    }


