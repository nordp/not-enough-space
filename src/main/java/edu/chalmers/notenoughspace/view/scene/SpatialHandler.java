package edu.chalmers.notenoughspace.view.scene;

import com.google.common.eventbus.Subscribe;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.input.InputManager;
import com.jme3.light.SpotLight;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.LightNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import edu.chalmers.notenoughspace.assets.AssetLoaderFactory;
import edu.chalmers.notenoughspace.assets.IModelLoader;
import edu.chalmers.notenoughspace.assets.ISoundLoader;
import edu.chalmers.notenoughspace.core.entity.*;
import edu.chalmers.notenoughspace.core.entity.beamable.Cow;
import edu.chalmers.notenoughspace.core.entity.beamable.Junk;
import edu.chalmers.notenoughspace.core.entity.enemy.Farmer;
import edu.chalmers.notenoughspace.core.entity.enemy.Hayfork;
import edu.chalmers.notenoughspace.core.entity.enemy.Satellite;
import edu.chalmers.notenoughspace.core.entity.powerup.EnergyPowerup;
import edu.chalmers.notenoughspace.core.entity.powerup.HealthPowerup;
import edu.chalmers.notenoughspace.core.entity.powerup.Powerup;
import edu.chalmers.notenoughspace.core.entity.ship.Beam;
import edu.chalmers.notenoughspace.core.entity.ship.Ship;
import edu.chalmers.notenoughspace.ctrl.*;
import edu.chalmers.notenoughspace.event.*;

/**
 * Handler for all the framework spatials in the game. Creates, adds and removes spatials and connects
 * them to their respective controls.
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
        System.out.println("Event reached.");

        Entity entity = event.getEntity();
        Node removedNode = (Node) rootNode.getChild(entity.getID());
        
        Control control;
        while (removedNode.getNumControls() > 0) {
            control = removedNode.getControl(0);
            
            if (control instanceof DetachableControl) {
                ((DetachableControl) control).onDetach();
            }
            removedNode.removeControl(control);
        }
        
        stopCorrespondingAudioNode(entity, removedNode);
        
        removedNode.removeFromParent();
    }

    @Subscribe
    public void entityCreated(EntityCreatedEvent event) {
        System.out.println("Event reached.");

        String name = event.getEntity().getID();
        Node node = new Node(name);
        System.out.println("Node created: " + node + "from ID: " + event.getEntity().getID());

        IModelLoader modelLoader = AssetLoaderFactory.getModelLoader();
        Entity entity = event.getEntity();
        
        Spatial model;
        AbstractControl control = createEmptyControl();
        Spatial parent = rootNode;
        
        if (entity instanceof Cow) {
            Cow cow = (Cow) entity;

            if (cow.isGolden()) {
                model = modelLoader.loadModel("goldenCow");
            } else {
                model = modelLoader.loadModel("cow");
            }
            model.setLocalTranslation(0, Planet.PLANET_RADIUS, 0);
            model.scale(cow.getSize());
            model.setName("model");

            node.setLocalRotation(otherSideOfPlanet());

            control = new CowControl(cow);
            parent = rootNode.getChild("planet");
            
        } else if (entity instanceof Junk) {
            String modelID = "";

            switch ((int) (FastMath.nextRandomFloat()*100) % 3){
                case 0:
                    modelID = "barn";
                    break;
                case 1:
                    modelID = "barrel";
                    break;
                case 2:
                    modelID = "tree";
                    break;
            }
            
            model = modelLoader.loadModel(modelID);
            model.setLocalTranslation(0, Planet.PLANET_RADIUS, 0);
            
            node.setLocalRotation(otherSideOfPlanet());
            
            control = new JunkControl((Junk) entity);
            parent = rootNode.getChild("planet");
        
        } else if (entity instanceof Ship) {
            model = modelLoader.loadModel("ship");
            model.setName("shipModel");
            model.move(0, Planet.PLANET_RADIUS + Ship.ALTITUDE, 0);

            addSpotLightUnderShip(model, node);
            addSpotLightAboveShip(model, node);
            
            control = new ShipControl(inputManager, app.getListener(), (Ship) entity);
        
        } else if (entity instanceof Satellite) {
            model = modelLoader.loadModel("satellite");
            model.setLocalTranslation(0,Planet.PLANET_RADIUS+1.3f,0);
            
            node.setLocalRotation(otherSideOfPlanet());
            
            control = new SatelliteControl((Satellite) entity);
        
        } else if (entity instanceof Beam){
            model = modelLoader.loadModel("beam");
            model.setName("beamModel");
            model.setLocalTranslation(0f, 0.24f, 0f);
            model.move(rootNode.getChild("shipModel").getLocalTranslation());
            
            control = new BeamControl();
            parent = rootNode.getChild("ship");
        
        } else if (entity instanceof Planet) {
            model = modelLoader.loadModel("planet");
            model.setName("planetModel");
    
        } else if (entity instanceof Farmer) {
            model = modelLoader.loadModel("farmer");
            model.setLocalTranslation(0, Planet.PLANET_RADIUS + 0.95f, 0);
            
            node.setLocalRotation(otherSideOfPlanet());
            control = new FarmerControl((Farmer) entity);
        
        } else if (entity instanceof Hayfork) {
            Hayfork hayfork = (Hayfork) entity;
            model = modelLoader.loadModel("hayfork");

            Entity thrower = hayfork.getThrower();
            
            javax.vecmath.Vector3f throwerWorldTranslation =
                    thrower.getPlanetaryInhabitant().getPosition();
            model.setLocalTranslation(new Vector3f(
                    throwerWorldTranslation.x,
                    throwerWorldTranslation.y,
                    throwerWorldTranslation.z));

            control = new HayforkControl(hayfork);
        
        } else if (entity instanceof Powerup){
            if (entity instanceof HealthPowerup) {
                model = modelLoader.loadModel("cow");
            } else {
                model = modelLoader.loadModel("cow");
            }
            
            model.setLocalTranslation(0, Planet.PLANET_RADIUS + Ship.ALTITUDE, 0);
            model.rotate(FastMath.DEG_TO_RAD * 25, FastMath.DEG_TO_RAD * 15, FastMath.DEG_TO_RAD * 15);
            //TODO: Move model rotation to ModelLoader when powerUp model is implemented.

            node.setLocalRotation(otherSideOfPlanet());
            control = new PowerupControl((Powerup) entity);

        } else {
            throw new IllegalArgumentException("Unknown entity (not in model package).");
        }

        node.attachChild(model);
        node.addControl(control);
        entity.setPlanetaryInhabitant(new JMEInhabitant(node));

        addCorrespondingAudioNode(entity, node);
        addCorrespondingEffectNode(entity, node);
        
        if(entity instanceof Ship) {
            ((ShipControl) control).attachThirdPersonView(app.getCamera());
        }

        ((Node) parent).attachChild(node);
    }

    @Subscribe
    public void hayforkHitShip(HayforkCollisionEvent event) {
        SoundPlayer.getInstance().play("hayforkHit");

        String hayforkID = event.getHayForkID();
        Spatial hayfork = rootNode.getChild(hayforkID);
        Spatial hayforkModel = ((Node) hayfork).getChild(0);
        Node shipNode = ((Node) rootNode.getChild("ship"));
        Spatial ship = shipNode.getChild("shipModel");

        Vector3f hayforkPositionInShipNode = new Vector3f();
        shipNode.worldToLocal(hayforkModel.getWorldTranslation(), hayforkPositionInShipNode);


        hayfork.removeControl(HayforkControl.class);
        shipNode.attachChild(hayforkModel); //It detaches automatically from whatever node it's currently on

        Vector3f shipPosition = ship.getWorldTranslation();
        Vector3f upVector = new com.jme3.math.Vector3f(0, shipPosition.z, -shipPosition.y);
        hayforkModel.setLocalTranslation(hayforkPositionInShipNode);
        hayforkModel.lookAt(shipPosition, upVector);
    }

    @Subscribe
    public void satelliteHitShip(SatelliteCollisionEvent event){
        String name = event.getSatellite().getID();
        Node parent = (Node) rootNode.getChild(name);
 
        Spatial explosion = EffectFactory.createEffect(app.getAssetManager(), "satelliteExplosion");
        explosion.setLocalTranslation(parent.getChild(0).getWorldTranslation());
        rootNode.attachChild(explosion);
        SoundPlayer.getInstance().play("explosion");
    }


    private void addSpotLightAboveShip(Spatial model, Node node) {
        SpotLight spotLight = new SpotLight();

        spotLight.setSpotRange(Ship.ALTITUDE + 2f);
        spotLight.setSpotOuterAngle(45 * FastMath.DEG_TO_RAD);
        spotLight.setSpotInnerAngle(5 * FastMath.DEG_TO_RAD);
        spotLight.setDirection(model.getWorldTranslation().mult(-1));
        spotLight.setName("shipSpotLight");
        
        LightNode spotLightNodeAboveShip = new LightNode("spotLightNodeAboveShip", spotLight);
        spotLightNodeAboveShip.setLocalTranslation(model.getWorldTranslation().add(0, 2f, 0));
        
        rootNode.addLight(spotLight);
        node.attachChild(spotLightNodeAboveShip);
    }

    private void addSpotLightUnderShip(Spatial model, Node node) {
        SpotLight spotLight = new SpotLight();
        
        spotLight.setSpotRange(Ship.ALTITUDE + 2f);
        spotLight.setSpotOuterAngle(45 * FastMath.DEG_TO_RAD);
        spotLight.setSpotInnerAngle(5 * FastMath.DEG_TO_RAD);
        spotLight.setDirection(model.getWorldTranslation().mult(-1));
        spotLight.setName("shipSpotLight");
        
        LightNode spotLightNode = new LightNode("shipSpotLightNode", spotLight);
        spotLightNode.setLocalTranslation(model.getWorldTranslation());
        
        rootNode.addLight(spotLight);
        node.attachChild(spotLightNode);
    }

    private Quaternion otherSideOfPlanet() {
        Spatial ship = rootNode.getChild("ship");
        Quaternion currentShipRotation = ship.getLocalRotation().clone();
        Quaternion otherSideOfPlanet = currentShipRotation.mult(new Quaternion(0,0,1,0));
        return otherSideOfPlanet;
    }
    
    private void addCorrespondingAudioNode(Entity entity, Node node) {
        ISoundLoader soundLoader = AssetLoaderFactory.getSoundLoader();
        
        if (entity instanceof Farmer) {
            AudioNode farmerAudio = soundLoader.loadSound("farmer");
            NodeUtil.setUpAudioNode(farmerAudio, 0.2f, 10, true, node, "audio");

        } else if (entity instanceof Cow) {
            AudioNode mooAudio = soundLoader.loadSound("cow");
            NodeUtil.setUpAudioNode(mooAudio, 0.2f, 10, false, node, "audio");
            mooAudio.play();

            AudioNode mooAudio2 = soundLoader.loadSound("cow2");
            NodeUtil.setUpAudioNode(mooAudio2, 0.2f, 10, false, node, "audio2");

            AudioNode mooAudio3 = soundLoader.loadSound("cow3");
            NodeUtil.setUpAudioNode(mooAudio3, 0.2f, 10, false, node, "audio3");

        } else if (entity instanceof Beam) {
            AudioNode beamAudio = soundLoader.loadSound("beam");
            NodeUtil.setUpAudioNode(beamAudio, 0.2f, 0.2f, true, node, "audio");

        } else if (entity instanceof Hayfork) {
            AudioNode swishAudio = soundLoader.loadSound("hayforkThrown");
            NodeUtil.setUpAudioNode(swishAudio, 0.4f, 15, false, node, "audio");
            swishAudio.play();
        }
    }

    private void stopCorrespondingAudioNode(Entity entity, Node node){
        if (entity instanceof Farmer) {
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
        if (entity instanceof Cow) {
            if(((Cow) entity).isGolden()){
                NodeUtil.setUpEffectNode(EffectFactory.createEffect(app.getAssetManager(), "goldGlitter"), node);
            }
            NodeUtil.setUpEffectNode(EffectFactory.createEffect(app.getAssetManager(), "sweat"), node);

        } else if (entity instanceof HealthPowerup) {
            NodeUtil.setUpEffectNode(EffectFactory.createEffect(app.getAssetManager(), "healthPowerup"), node);

        } else if (entity instanceof EnergyPowerup) {
            NodeUtil.setUpEffectNode(EffectFactory.createEffect(app.getAssetManager(), "energyPowerup"), node);
        }
    }
    
    private AbstractControl createEmptyControl() {
        return new AbstractControl() {
            @Override
            protected void controlUpdate(float v) {}

            @Override
            protected void controlRender(RenderManager renderManager, ViewPort viewPort) {}
        };
    }
    
}
