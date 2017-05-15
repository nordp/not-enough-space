package edu.chalmers.notenoughspace.view;

import com.google.common.eventbus.Subscribe;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.light.SpotLight;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.LightNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.core.*;
import edu.chalmers.notenoughspace.ctrl.*;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * Created by Phnor on 2017-05-08.
 */
public class SpatialHandler {

    private SimpleApplication app;
    private Node rootNode;
    private InputManager inputManager;

    public SpatialHandler(SimpleApplication app){
        this.app = app;
        this.rootNode = app.getRootNode();
        this.inputManager = app.getInputManager();
        Bus.getInstance().register(this);
        System.out.println("Registered?");
    }

    @Subscribe
    public void entityCreated(EntityCreatedEvent event) {
        System.out.println("Event reached");

        Node node = new Node(event.entity.toString());
        Spatial model;
        AbstractControl control;
        Spatial parent = rootNode;

        if (event.entity instanceof Cow) {
            model = ModelLoaderFactory.getModelLoader().loadModel("cow");
            model.setLocalTranslation(0, Planet.PLANET_RADIUS, 0);

            control = new CowControl((Cow) event.entity);
            parent = rootNode.getChild("planet");
        } else if (event.entity instanceof Junk){
            model = ModelLoaderFactory.getModelLoader().loadModel("junk");
            model.setLocalTranslation(0, Planet.PLANET_RADIUS, 0);
            model.scale(0.01f, 0.01f, 0.01f);

            control = new JunkControl();
            parent = rootNode.getChild("planet");
        } else if (event.entity instanceof Ship) {
            model = ModelLoaderFactory.getModelLoader().loadModel("ship");
            model.setName("shipModel");
            model.scale(0.02f, 0.02f, 0.02f);
            model.move(0, Planet.PLANET_RADIUS + Ship.ALTITUDE, 0);

            node.setName("ship");

            SpotLight spotLight = new SpotLight();
            spotLight.setSpotRange(10);
            spotLight.setSpotOuterAngle(45 * FastMath.DEG_TO_RAD);
            spotLight.setSpotInnerAngle(5 * FastMath.DEG_TO_RAD);
//            spotLight.setPosition(model.getWorldTranslation());
            spotLight.setDirection(model.getWorldTranslation().mult(-1));
            spotLight.setName("shipSpotLight");
            LightNode spotLightNode = new LightNode("shipSpotLightNode", spotLight);
            spotLightNode.setLocalTranslation(model.getWorldTranslation());
            rootNode.addLight(spotLight);
            node.attachChild(spotLightNode);
            /**
             * Moves the ship core from its original position at the center of the Ship node
             * to it's correct starting position over the planet's surface.
             *
             * @param planetRadius The radius of the planet that the ship is hovering over.
             * @param shipAltitude The ship's height above the planet's surface.
             */
            control = new ShipControl(inputManager, (Ship) event.entity);
            node.setName("ship");
        } else if (event.entity instanceof Satellite){
            model = ModelLoaderFactory.getModelLoader().loadModel("satellite");
            model.setLocalTranslation(0,Planet.PLANET_RADIUS+2,0);
            model.scale(0.01f, 0.01f, 0.01f);

            control = new SatelliteControl();
        } else if (event.entity instanceof Beam){
            model = ModelLoaderFactory.getModelLoader().loadModel("beam");
            model.setName("beamModel");
            model.setLocalTranslation(0f, 0.24f, 0f);
            model.move(rootNode.getChild("shipModel").getLocalTranslation());
            node.setName("beam");
            control = new BeamControl();
            parent = rootNode.getChild("ship");
        } else if (event.entity instanceof Planet) {
            model = ModelLoaderFactory.getModelLoader().loadModel("planet");
            model.setName("planetModel");
            node.setName("planet");
            control = new PlanetControl((Planet) event.entity);
        } else if (event.entity instanceof Farmer) {
            model = ModelLoaderFactory.getModelLoader().loadModel("farmer");
            control = new FarmerControl((Farmer) event.entity);
            model.setLocalTranslation(0, Planet.PLANET_RADIUS, 0);
            model.scale(0.01f, 0.01f, 0.01f);
        } else if (event.entity instanceof Hayfork) {
            Hayfork hayfork = (Hayfork) event.entity;
            model = ModelLoaderFactory.getModelLoader().loadModel("hayfork");

            Entity thrower = hayfork.getThrower();
            javax.vecmath.Vector3f throwerWorldTranslation =
                    thrower.getPlanetaryInhabitant().getWorldTranslation();

            model.setLocalTranslation(new Vector3f(throwerWorldTranslation.x,
                    throwerWorldTranslation.y,
                    throwerWorldTranslation.z));

            control = new HayforkControl(hayfork);
        } else {
            throw new IllegalArgumentException("entity must be Entity from model package");
        }

        node.attachChild(model);
        node.addControl(control);

        event.entity.setPlanetaryInhabitant(new JMEInhabitant(node));

        //Temporary place, maybe move somewhere else and/or bind to key
        if(event.entity instanceof Ship)
             ((ShipControl)control).attachThirdPersonView(app.getCamera(), Planet.PLANET_RADIUS, Ship.ALTITUDE);

        //All entities get one geometry and one node each. The parent node of each entity has the name of the entity
        ((Node)parent).attachChild(node);
        //rootNode.detachChildNamed(event.entity.toString());
    }

}
