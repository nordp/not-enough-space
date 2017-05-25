package edu.chalmers.notenoughspace.ctrl;

import com.jme3.app.SimpleApplication;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.core.entity.beamable.Cow;
import edu.chalmers.notenoughspace.core.entity.Planet;
import edu.chalmers.notenoughspace.core.entity.beamable.Junk;
import edu.chalmers.notenoughspace.core.entity.enemy.Farmer;
import edu.chalmers.notenoughspace.core.EntitySpawner;
import edu.chalmers.notenoughspace.view.scene.SpatialHandler;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Phnor on 2017-05-09.
 */
public class SpatialHandlerTest {
    @Test
    public void entityAttachedEvent() throws Exception {
        SimpleApplication app = new SimpleApplication() {
            @Override
            public void simpleInitApp() {
                assertNotNull(getInputManager());
                assertNotNull(getAssetManager());



                assertNotNull(getRootNode());

                SpatialHandler handler = new SpatialHandler();
                handler.setApp(this);

                ModelLoaderFactory.setAssetManager(getAssetManager());

                Planet p = new Planet();
                EntitySpawner s = new EntitySpawner(p);

                s.spawn(Cow.class);

                assertTrue(getRootNode().getChildren().size() == 2);

                assertTrue(getRootNode().getChild(0).getName().equals(p.toString()));


                s.spawn(Cow.class);
                s.spawn(Junk.class, 2);
                s.spawn(Farmer.class);

                assertTrue(getRootNode().getChildren().size() == 4);

                Cow c = new Cow();

                assertTrue(getRootNode().getChild(p.toString()).equals(p));
                assertTrue(getRootNode().getChild(c.toString()).equals(c));

            }
        };
        app.setShowSettings(false);
        app.start();

    }

}