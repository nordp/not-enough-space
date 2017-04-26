
    package edu.chalmers.notenoughspace.nodes;

import com.jme3.asset.AssetManager;
import com.jme3.scene.*;

    public class SatelliteNode extends Node{

        private float height;
        private AssetManager assetManager;


        public SatelliteNode(float height, AssetManager assetManager){
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
            //satellite.addControl(new SatelliteControl((Node) satellite, new SatelliteNode(height, assetManager)));

            return satelliteModel;
        }



        private SatelliteNode getMe() {
            return this;
        }

    }


