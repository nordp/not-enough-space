package edu.chalmers.notenoughspace;

        import com.jme3.app.SimpleApplication;
        import com.jme3.audio.AudioData;
        import com.jme3.audio.AudioNode;
        import com.jme3.light.DirectionalLight;
        import com.jme3.light.SpotLight;
        import com.jme3.material.Material;
        import com.jme3.math.ColorRGBA;
        import com.jme3.math.FastMath;
        import com.jme3.math.Vector3f;
        import com.jme3.renderer.RenderManager;
        import com.jme3.scene.Geometry;
        import com.jme3.scene.Mesh;
        import com.jme3.scene.Node;
        import com.jme3.scene.VertexBuffer;
        import com.jme3.scene.shape.Box;
        import com.jme3.scene.shape.Sphere;
        import com.jme3.system.AppSettings;
        import edu.chalmers.notenoughspace.Assets.ModelLoaderFactory;

        import java.awt.*;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Game extends SimpleApplication {

    public static void main(String[] args) {
        Game app = new Game();

        //Removes the settings window at the start:
        app.showSettings = false; //Look at how to get fullscreen!
        AppSettings appSettings = new AppSettings(true);
//        appSettings.put("Width", 1020);
//        appSettings.put("Height", 800);
        appSettings.put("Title", "Yahoooooo!");

        DisplayMode mode = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDisplayMode();
        appSettings.setResolution(mode.getWidth(), mode.getHeight());
        appSettings.setFrequency(mode.getRefreshRate());

        appSettings.setVSync(true);
        appSettings.setFullscreen(true);

        app.setPauseOnLostFocus(true);
        app.setSettings(appSettings);


        app.start();
    }

    private void setFullScreen() {


    }

//    private Geometry blob;

    @Override
    public void simpleInitApp() {
        setFullScreen();
        setGoodSpeed();
        ModelLoaderFactory.setAssetManager(assetManager);
        stateManager.attach(new Round(assetManager, inputManager));

//        Material mat = new Material(assetManager,
//                "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setColor("Color", ColorRGBA.Yellow);


        //Beamer light:
        //spot.setColor(ColorRGBA.Blue); //Doesn't work?!
//        rootNode.addLight(spot);

        //Point blob
        //Sphere sp = new Sphere(100, 100, 0.6f);

//        Box sp = new Box(0.1f, 0.2f, 0.15f);
//        blob = new Geometry("blob", sp);
//        Material mat3 = new Material(assetManager,
//                "Common/MatDefs/Light/Lighting.j3md");
//        mat3.setBoolean("UseMaterialColors",true);
//        mat3.setColor("Diffuse",ColorRGBA.Red);
//        mat3.setColor("Specular",ColorRGBA.Red);
//        mat3.setFloat("Shininess", 64f);  // [0,128]
//        blob.setMaterial(mat3);
//        blob.move(0, 3.4f, 0);
//        //blob.scale(2, 1.4f, 1);
//        blob.addControl(new BallControl(player, this));
//        Node blobPivotNode = new Node();
//        blobPivotNode.attachChild(blob);
//        rootNode.attachChild(blobPivotNode);


        // What?
//        Sphere planet3 = new Sphere(30, 30, 0.1f);
//        Geometry gp3 = new Geometry("p3", planet3);
//        gp3.setMaterial(mat);
//        gp3.move(0, 0, 1);
//        blobPivotNode.attachChild(gp3);
//
//        Sphere planet4 = new Sphere(30, 30, 0.05f);
//        Geometry gp4 = new Geometry("p4", planet4);
//        gp4.setMaterial(mat);
//        gp4.move(0, 0.5f, 0);
//        blobPivotNode.attachChild(gp4);

        //myPlanet.setCullHint(Spatial.CullHint.Always);

        //rootNode.move(0, 0, -1);


//        for (int i = 0; i < 50; i++) {                   //TODO Replace with planet.populate();
//            createBlob();
//        }


    }

//    public Geometry makeLine(Vector3f from, Vector3f to, ColorRGBA color) {
//        //LIne
//        Material mat = new Material(assetManager,
//                "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setColor("Color", color);
//
//        Mesh lineMesh = new Mesh();
//        lineMesh.setMode(Mesh.Mode.Lines);
//        lineMesh.setBuffer(VertexBuffer.Type.Position, 3,
//                new float[]{ from.x, from.y, from.z, to.x, to.y, to.z});
//        lineMesh.setBuffer(VertexBuffer.Type.Index, 2, new short[]{ 0, 1 });
//        //lineMesh.updateBound();
//        //lineMesh.updateCounts();
//        Geometry lineGeometry = new Geometry("line", lineMesh);
//        //Material lineMaterial = getAssetManager().loadMaterial("red_color.j3m");
//        lineGeometry.setMaterial(mat);
//        return lineGeometry;
//    }

//    private void createBlob() {
//        Box sp = new Box(0.1f, 0.2f, 0.15f);
//        Geometry blob = new Geometry("blob", sp);
//        Material mat3 = new Material(assetManager,
//                "Common/MatDefs/Light/Lighting.j3md");
//        mat3.setBoolean("UseMaterialColors",true);
//        mat3.setColor("Diffuse",ColorRGBA.Red);
//        mat3.setColor("Specular",ColorRGBA.Red);
//        mat3.setFloat("Shininess", 64f);  // [0,128]
//        blob.setMaterial(mat3);
//        blob.move(0, 3.4f, 0);
//        //blob.scale(2, 1.4f, 1);
//        blob.addControl(new BallControl(player, this));
//        Node blobPivotNode = new Node();
//        blobPivotNode.attachChild(blob);
//        blobPivotNode.rotate(FastMath.rand.nextInt(360)*FastMath.DEG_TO_RAD,
//                FastMath.rand.nextInt(360)*FastMath.DEG_TO_RAD,
//                FastMath.rand.nextInt(360)*FastMath.DEG_TO_RAD);
//        rootNode.attachChild(blobPivotNode);
//    }

    /** Custom Keybinding: Map named actions to inputs. */

    @Override
    public void simpleUpdate(float tpf) {

        //listener.setLocation(player.getLocalTranslation());
        //listener.setRotation(player.getWorldRotation());
//        if (blob.getWorldTranslation().distance(Vector3f.ZERO) > 3.4f) {
//            blob.move(blob.getLocalTranslation().mult(-1).normalize().mult(0.001f));
//        }

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }


    private void setGoodSpeed() {
        this.flyCam.setMoveSpeed(50);
        this.setDisplayFps(false);
        this.setDisplayStatView(false);
    }
}
