package edu.chalmers.notenoughspace.ctrl;

import com.google.common.eventbus.Subscribe;
import com.jme3.input.InputManager;
import com.jme3.light.SpotLight;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.core.Cow;
import edu.chalmers.notenoughspace.core.Planet;
import edu.chalmers.notenoughspace.core.Ship;
import edu.chalmers.notenoughspace.core.Spatial3D;
import edu.chalmers.notenoughspace.event.AttachedEvent;
import edu.chalmers.notenoughspace.event.Bus;

import javax.jws.WebParam;

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
    public void entityAttachedEvent(AttachedEvent event){
        System.out.println("Event reached");

        if (event.attached) {
            Node node;
            Spatial model;
            AbstractControl control;

            if (event.child instanceof Cow) {
                model = ModelLoaderFactory.getModelLoader().loadModel("cow");
                control = new CowControl((Cow) event.child);
            } else if (event.child instanceof Ship) {
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

                control = new ShipControl(inputManager, (Ship) event.child);
            } else if (event.child instanceof Planet){
                model = ModelLoaderFactory.getModelLoader().loadModel("planet");
                System.out.println("Planet loaded");
                control = new PlanetControl((Planet) event.child);
            } else {
                throw new IllegalArgumentException("entity must be Spatial3D from model package");
            }
            node = new Node(event.child.toString());
            node.attachChild(model);
            if (event.parent != null){
                ((Node)rootNode.getChild(event.parent.toString())).attachChild(node);
            } else {
                rootNode.attachChild(node);
            }
            node.addControl(control);
        } else {
            rootNode.detachChildNamed(event.child.toString());
        }
    }
}
