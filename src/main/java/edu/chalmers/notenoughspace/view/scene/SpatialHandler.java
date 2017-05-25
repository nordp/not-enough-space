package edu.chalmers.notenoughspace.view.scene;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.input.InputManager;
import com.jme3.light.SpotLight;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.LightNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.core.entity.*;
import edu.chalmers.notenoughspace.core.entity.beamable.Cow;
import edu.chalmers.notenoughspace.core.entity.beamable.Junk;
import edu.chalmers.notenoughspace.core.entity.enemy.Farmer;
import edu.chalmers.notenoughspace.core.entity.enemy.Hayfork;
import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;
import edu.chalmers.notenoughspace.core.entity.ship.Beam;
import edu.chalmers.notenoughspace.core.entity.beamable.BeamableEntity;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.ctrl.*;
import edu.chalmers.notenoughspace.event.*;

/**
 * Created by Phnor on 2017-05-08.
 */
public class SpatialHandler {

    private SimpleApplication app;
    private Node rootNode;
    private InputManager inputManager;

    public SpatialHandler(){
        Bus.getInstance().register(this);
    }

    public void setApp(SimpleApplication app){
        this.app = app;
        this.inputManager = app.getInputManager();
    }

    public void setRootNode(Node rootNode){
        this.rootNode = rootNode;
    }

    @Subscribe
    public void beamableStored(BeamableStoredEvent event){
        SoundPlayer.getInstance().play("beamed");
    }

    @Subscribe
    public void entityRemoved(EntityRemovedEvent event) {
        System.out.println("Event reached");

        Entity entity = event.getEntity();

        Node removedNode = (Node) rootNode.getChild(entity.getID());

        Control control;
        while(removedNode.getNumControls() > 0) {
            control = removedNode.getControl(0);
            if(control instanceof DetachableControl)
                ((DetachableControl) control).onDetach();
            removedNode.removeControl(control);
        }
        stopCorrespondingAudioNode(entity, removedNode);
        removedNode.removeFromParent();
    }

    @Subscribe
    public void entityCreated(EntityCreatedEvent event) {
        System.out.println("Event reached");

        Node node = new Node(event.getEntity().getID());
        System.out.println("Node created: " + node + "from ID: " + event.getEntity().getID());
        Spatial model;
        AbstractControl control;
        Spatial parent = rootNode;

        if (event.getEntity() instanceof Cow) {
            Cow cow = (Cow) event.getEntity();
            if(cow.isGolden()) {
                model = ModelLoaderFactory.getModelLoader().loadModel("goldenCow");

            }else {
                model = ModelLoaderFactory.getModelLoader().loadModel("cow");
            }
            model.setLocalTranslation(0, Planet.PLANET_RADIUS, 0);
            model.scale(cow.getSize());
            model.setName("model");
            node.setLocalRotation(rootNode.getChild("ship").getLocalRotation().clone().mult(new Quaternion(0,0,1,0))); // TEMPORARY
            /*
            if(cow.isGolden()){
                setMaterial? model = new model?
            }*/

            control = new CowControl(cow);
            parent = rootNode.getChild("planet");
        } else if (event.getEntity() instanceof Junk){
            model = ModelLoaderFactory.getModelLoader().loadModel("junk");
            model.setLocalTranslation(0, Planet.PLANET_RADIUS, 0);
            model.scale(0.013f, 0.013f, 0.013f);
            node.setLocalRotation(rootNode.getChild("ship").getLocalRotation().clone().mult(new Quaternion(0,0,1,0))); // TEMPORARY
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
            node.setLocalRotation(rootNode.getChild("ship").getLocalRotation().clone().mult(new Quaternion(0,0,1,0))); // TEMPORARY
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
        addCorrespondingEffectNode(event.getEntity(), node);
        
        //Temporary place, maybe move somewhere else and/or bind to key
        if(event.getEntity() instanceof Ship) {
            ((ShipControl) control).attachThirdPersonView(app.getCamera(), Planet.PLANET_RADIUS, Ship.ALTITUDE);
        }

        //All entity get one geometry and one node each. The parent node of each entity has the name of the entity
        ((Node)parent).attachChild(node);
        //rootNode.detachChildNamed(event.entity.toString());
    }

    @Subscribe
    public void hayforkHitShip(HayforkHitEvent event) {
        SoundPlayer.getInstance().play("hayforkHit");

        String hayforkID = event.getHayFork().getID();
        Spatial hayfork = rootNode.getChild(hayforkID);
        Spatial hayforkModel = ((Node) hayfork).getChild(0);

        hayfork.removeControl(HayforkControl.class);

        Node shipNode = ((Node) rootNode.getChild("ship"));
        Vector3f hayforkPositionInShipNode = new Vector3f();
        shipNode.worldToLocal(hayforkModel.getWorldTranslation(), hayforkPositionInShipNode);

        Spatial ship = shipNode.getChild("shipModel");

        shipNode.attachChild(hayforkModel); //It detaches automatically from whatever node it's currently on

        Vector3f direction = ship.getWorldTranslation();
        hayforkModel.setLocalTranslation(hayforkPositionInShipNode);
        hayforkModel.lookAt(direction, new com.jme3.math.Vector3f(0, direction.z, -direction.y));
        //The above is really a bit of a hack. TODO: Find out how to keep the rotation when moving between nodes.
    }

    private void addCorrespondingAudioNode(Entity entity, Node node) {
        if (entity instanceof Farmer) {
            AudioNode farmerAudio = ModelLoaderFactory.getSoundLoader().loadSound("farmer");
            NodeUtil.setUpAudioNode(farmerAudio, 0.2f, 10, true, node, "audio");
            farmerAudio.play();
        } else if (entity instanceof Cow) {
            AudioNode mooAudio = ModelLoaderFactory.getSoundLoader().loadSound("cow");
            NodeUtil.setUpAudioNode(mooAudio, 0.2f, 10, false, node, "audio");
            mooAudio.play();

            AudioNode mooAudio2 = ModelLoaderFactory.getSoundLoader().loadSound("cow2");
            NodeUtil.setUpAudioNode(mooAudio2, 0.2f, 10, false, node, "audio2");

            AudioNode mooAudio3 = ModelLoaderFactory.getSoundLoader().loadSound("cow3");
            NodeUtil.setUpAudioNode(mooAudio3, 0.2f, 10, false, node, "audio3");
        } else if (entity instanceof Beam) {
            AudioNode beamAudio = ModelLoaderFactory.getSoundLoader().loadSound("beam");
            NodeUtil.setUpAudioNode(beamAudio, 0.2f, 0.2f, true, node, "audio");
        } else if (entity instanceof Hayfork) {
            AudioNode swishAudio = ModelLoaderFactory.getSoundLoader().loadSound("hayforkThrown");
            NodeUtil.setUpAudioNode(swishAudio, 0.4f, 15, false, node, "audio");
            swishAudio.play();
        }
    }

    private void stopCorrespondingAudioNode(Entity entity, Node node){
        if(entity instanceof Farmer){
            ((AudioNode)node.getChild("audio")).stop();
        } else if (entity instanceof Cow) {
            ((AudioNode)node.getChild("audio")).stop();
            ((AudioNode)node.getChild("audio2")).stop();
            ((AudioNode)node.getChild("audio3")).stop();
        } else if (entity instanceof Beam) {
            ((AudioNode)node.getChild("audio")).stop();
        } else if (entity instanceof Hayfork) {
            ((AudioNode)node.getChild("audio")).stop();
        }
    }

    private void addCorrespondingEffectNode(Entity entity, Node node){
        if(entity instanceof Cow) {
            if(((Cow)entity).isGolden()){
                NodeUtil.setUpEffectNode(EffectFactory.createEffect(app.getAssetManager(), "goldGlitter"), node);
            }
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
