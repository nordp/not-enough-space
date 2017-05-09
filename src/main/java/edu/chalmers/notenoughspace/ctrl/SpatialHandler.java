package edu.chalmers.notenoughspace.ctrl;

import com.google.common.eventbus.Subscribe;
import com.jme3.input.InputManager;
import com.jme3.light.SpotLight;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.core.*;
import edu.chalmers.notenoughspace.event.EntityCreatedEvent;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * Created by Phnor on 2017-05-08.
 */
public class SpatialHandler {

    private Node rootNode;
    private InputManager inputManager;

    public SpatialHandler(Node rootNode, InputManager inputManager){
        this.rootNode = rootNode;
        this.inputManager = inputManager;
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
            control = new CowControl((Cow) event.entity);
            parent = rootNode.getChild("planet");
        } else if (event.entity instanceof Junk){
            model = ModelLoaderFactory.getModelLoader().loadModel("junk");
            parent = rootNode.getChild("planet");
            control = new JunkControl();
        } else if (event.entity instanceof Ship) {
            model = ModelLoaderFactory.getModelLoader().loadModel("ship");

            SpotLight spotLight = new SpotLight();
            spotLight.setSpotRange(10);
            spotLight.setSpotOuterAngle(45 * FastMath.DEG_TO_RAD);
            spotLight.setSpotInnerAngle(5 * FastMath.DEG_TO_RAD);
            spotLight.setPosition(model.getWorldTranslation());
            spotLight.setDirection(model.getWorldTranslation().mult(-1));
            spotLight.setName("shipSpotLight");
            rootNode.addLight(spotLight);
            /**
             * Moves the ship core from its original position at the center of the Ship node
             * to it's correct starting position over the planet's surface.
             *
             * @param planetRadius The radius of the planet that the ship is hovering over.
             * @param shipAltitude The ship's height above the planet's surface.
             */
            //shipModel.move(0, planetRadius + shipAltitude, 0);
            control = new ShipControl(inputManager, (Ship) event.entity);

        } else if (event.entity instanceof Satellite){
            model = ModelLoaderFactory.getModelLoader().loadModel("satellite");
            control = new SatelliteControl();
        } else if (event.entity instanceof Beam){
            model = ModelLoaderFactory.getModelLoader().loadModel("beam");
            control = new BeamControl();
            parent = rootNode.getChild("ship");
        } else if (event.entity instanceof Planet) {
            model = ModelLoaderFactory.getModelLoader().loadModel("planet");
            control = new PlanetControl((Planet) event.entity);
            node.setName("planet");
        } else {
            throw new IllegalArgumentException("entity must be Entity from model package");
        }

        node.attachChild(model);
        node.addControl(control);

        //All entities get one geometry and one node each. The parent node of each entity has the name of the entity
        ((Node)parent).attachChild(node);
        //rootNode.detachChildNamed(event.entity.toString());
    }
}
