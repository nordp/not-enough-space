package edu.chalmers.notenoughspace.ctrl;

import com.jme3.app.SimpleApplication;
import edu.chalmers.notenoughspace.assets.ModelLoaderFactory;
import edu.chalmers.notenoughspace.core.Cow;
import edu.chalmers.notenoughspace.core.Planet;
import edu.chalmers.notenoughspace.view.SpatialHandler;
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

                SpatialHandler handler = new SpatialHandler(this);

                ModelLoaderFactory.setAssetManager(getAssetManager());

                Planet p = new Planet();

                p.populate(1,0,0);

                assertTrue(getRootNode().getChildren().size() == 2);

                assertTrue(getRootNode().getChild(0).getName().equals(p.toString()));


                p.populate(1,2,0);

                assertTrue(getRootNode().getChildren().size() == 4);

                Cow c = new Cow();

                assertTrue(getRootNode().getChild(p.toString()).equals(p));
                assertTrue(getRootNode().getChild(c.toString()).equals(c));

            }
        };

        app.start();

    }

}