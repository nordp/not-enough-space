package edu.chalmers.notenoughspace.view;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.input.InputManager;
import com.jme3.light.SpotLight;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.LightNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.assets.SoundPlayer;
import edu.chalmers.notenoughspace.core.*;
import edu.chalmers.notenoughspace.ctrl.*;
import edu.chalmers.notenoughspace.event.*;
import edu.chalmers.notenoughspace.utils.AudioNodeUtil;

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
    public void entityStored(EntityRemovedEvent event) {
        System.out.println("Event reached");

        Entity entity = event.getEntity();

        if (entity instanceof BeamableEntity) {
            SoundPlayer.getInstance().play("beamed");
        }

        Spatial storedObject = rootNode.getChild(entity.getID());
        storedObject.removeControl(AbstractControl.class);
        storedObject.removeFromParent();
    }

    @Subscribe
    public void entityCreated(EntityCreatedEvent event) {
        System.out.println("Event reached");

        Node node = new Node(event.getEntity().getID());
        Spatial model;
        AbstractControl control;
        Spatial parent = rootNode;

        if (event.getEntity() instanceof Cow) {
            Cow cow = (Cow) event.getEntity();
            if(cow.isGolden())
                model = ModelLoaderFactory.getModelLoader().loadModel("goldenCow");
            else
                model = ModelLoaderFactory.getModelLoader().loadModel("cow");
            model.setLocalTranslation(0, Planet.PLANET_RADIUS, 0);
            model.scale(cow.getSize());

            if(cow.isGolden()){
                //setMaterial? model = new model?
            }

            control = new CowControl(cow);
            parent = rootNode.getChild("planet");
        } else if (event.getEntity() instanceof Junk){
            model = ModelLoaderFactory.getModelLoader().loadModel("junk");
            model.setLocalTranslation(0, Planet.PLANET_RADIUS, 0);
            model.scale(0.013f, 0.013f, 0.013f);
            node.rotate(-FastMath.PI/6, 0, FastMath.PI/15);
            control = new JunkControl((Junk) event.getEntity());
            parent = rootNode.getChild("planet");
        } else if (event.getEntity() instanceof Ship) {
            model = ModelLoaderFactory.getModelLoader().loadModel("ship");
            model.setName("shipModel");
            model.scale(0.02f, 0.02f, 0.02f);
            model.move(0, Planet.PLANET_RADIUS + Ship.ALTITUDE, 0);

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

            //Add spotlight ABOVE ship:
            SpotLight spotLightAboveShip = spotLight.clone();
            LightNode spotLightNodeAboveShip = new LightNode("spotLightNodeAboveShip", spotLightAboveShip);
            spotLightNodeAboveShip.setLocalTranslation(model.getWorldTranslation().add(0, 2f, 0));
            rootNode.addLight(spotLightAboveShip);
            node.attachChild(spotLightNodeAboveShip);
            /**
             * Moves the ship core from its original position at the center of the Ship node
             * to it's correct starting position over the planet's surface.
             *
             * @param planetRadius The radius of the planet that the ship is hovering over.
             * @param shipAltitude The ship's height above the planet's surface.
             */
            control = new ShipControl(inputManager, app.getListener(), (Ship) event.getEntity());
        } else if (event.getEntity() instanceof Satellite){
            model = ModelLoaderFactory.getModelLoader().loadModel("satellite");
            model.setLocalTranslation(0,Planet.PLANET_RADIUS+1.3f,0);
            model.rotate(FastMath.DEG_TO_RAD * 25, FastMath.DEG_TO_RAD * 15, FastMath.DEG_TO_RAD * 15);
            model.scale(0.15f);
            node.rotate(1f, 0f, 0f); // TEMPORARY
            control = new SatelliteControl((Satellite) event.getEntity());
        } else if (event.getEntity() instanceof Beam){
            model = ModelLoaderFactory.getModelLoader().loadModel("beam");
            model.setName("beamModel");
            model.setLocalTranslation(0f, 0.24f, 0f);
            model.move(rootNode.getChild("shipModel").getLocalTranslation());
            control = new BeamControl();
            parent = rootNode.getChild("ship");
        } else if (event.getEntity() instanceof Planet) {
            model = ModelLoaderFactory.getModelLoader().loadModel("planet");
            model.setName("planetModel");
            control = new PlanetControl((Planet) event.getEntity());
        } else if (event.getEntity() instanceof Farmer) {
            model = ModelLoaderFactory.getModelLoader().loadModel("farmer");
            control = new FarmerControl((Farmer) event.getEntity());
            model.setLocalTranslation(0, Planet.PLANET_RADIUS + 0.95f/*remove*/, 0);
            model.scale(0.01f, 0.01f, 0.01f);
        } else if (event.getEntity() instanceof Hayfork) {
            Hayfork hayfork = (Hayfork) event.getEntity();
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

        event.getEntity().setPlanetaryInhabitant(new JMEInhabitant(node));

        //Maybe this should not be done here?
        addCorrespondingAudioNode(event.getEntity(), node);
        
        //Temporary place, maybe move somewhere else and/or bind to key
        if(event.getEntity() instanceof Ship) {
            ((ShipControl) control).attachThirdPersonView(app.getCamera(), Planet.PLANET_RADIUS, Ship.ALTITUDE);
        }

        //All entities get one geometry and one node each. The parent node of each entity has the name of the entity
        ((Node)parent).attachChild(node);
        //rootNode.detachChildNamed(event.entity.toString());
    }

    private void addCorrespondingAudioNode(Entity entity, Node node) {
        if (entity instanceof Farmer) {
            AudioNode farmerAudio = ModelLoaderFactory.getSoundLoader().loadSound("farmer");
            AudioNodeUtil.setUpAudioNode(farmerAudio, 0.2f, 10, true, node, "audio");
            farmerAudio.play();
        } else if (entity instanceof Cow) {
            AudioNode mooAudio = ModelLoaderFactory.getSoundLoader().loadSound("cow");
            AudioNodeUtil.setUpAudioNode(mooAudio, 0.2f, 10, false, node, "audio");
            mooAudio.play();

            AudioNode mooAudio2 = ModelLoaderFactory.getSoundLoader().loadSound("cow2");
            AudioNodeUtil.setUpAudioNode(mooAudio2, 0.2f, 10, false, node, "audio2");

            AudioNode mooAudio3 = ModelLoaderFactory.getSoundLoader().loadSound("cow3");
            AudioNodeUtil.setUpAudioNode(mooAudio3, 0.2f, 10, false, node, "audio3");
        } else if (entity instanceof Beam) {
            AudioNode beamAudio = ModelLoaderFactory.getSoundLoader().loadSound("beam");
            AudioNodeUtil.setUpAudioNode(beamAudio, 0.2f, 0.2f, true, node, "audio");
        } else if (entity instanceof Hayfork) {
            AudioNode swishAudio = ModelLoaderFactory.getSoundLoader().loadSound("hayforkThrown");
            AudioNodeUtil.setUpAudioNode(swishAudio, 0.4f, 15, false, node, "audio");
            swishAudio.play();
        }
    }

    @Subscribe
    public void satelliteCollision(SatelliteCollisionEvent event){
        Node parent = (Node) rootNode.getChild(event.getSatellite().getID());
        Spatial explosion = EffectFactory.createEffect(app.getAssetManager(), "satelliteExplosion");
        explosion.setLocalTranslation(parent.getChild(0).getWorldTranslation());
        rootNode.attachChild(explosion);
        
        SoundPlayer.getInstance().play("explosion");
    }

}
