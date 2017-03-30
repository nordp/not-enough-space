package edu.chalmers.notenoughspace;

        import com.jme3.app.SimpleApplication;
        import com.jme3.audio.AudioData;
        import com.jme3.audio.AudioNode;
        import com.jme3.input.KeyInput;
        import com.jme3.input.controls.AnalogListener;
        import com.jme3.input.controls.KeyTrigger;
        import com.jme3.light.DirectionalLight;
        import com.jme3.light.SpotLight;
        import com.jme3.material.Material;
        import com.jme3.math.ColorRGBA;
        import com.jme3.math.FastMath;
        import com.jme3.math.Vector3f;
        import com.jme3.renderer.RenderManager;
        import com.jme3.scene.CameraNode;
        import com.jme3.scene.Geometry;
        import com.jme3.scene.Mesh;
        import com.jme3.scene.Node;
        import com.jme3.scene.Spatial;
        import com.jme3.scene.VertexBuffer;
        import com.jme3.scene.control.CameraControl;
        import com.jme3.scene.shape.Box;
        import com.jme3.scene.shape.Sphere;
        import com.jme3.system.AppSettings;
        import com.jme3.util.TangentBinormalGenerator;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Game extends SimpleApplication {

    private AudioNode happy;

    public static void main(String[] args) {
        Game app = new Game();

        //Removes the settings window at the start:
        app.showSettings = false; //Look at how to get fullscreen!
        AppSettings appSettings = new AppSettings(true);
        appSettings.put("Width", 1020);
        appSettings.put("Height", 800);
        appSettings.put("Title", "Yahoooooo!");

        app.setPauseOnLostFocus(true);
        app.setSettings(appSettings);


        app.start();
    }

    private void setFullScreen() {


    }

    protected Geometry player;
    private SpotLight spot;
    private Geometry blob;

    @Override
    public void simpleInitApp() {
        setFullScreen();
        setGoodSpeed();


        //Player:
        Box b = new Box(0.2f, 0.1f, 0.2f);
        player = new Geometry("blue cube", b);
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);
        player.setMaterial(mat);
        player.move(0, 5f, 0);

        //Beamer light:
        spot = new SpotLight();
        spot.setSpotRange(100);
        spot.setSpotOuterAngle(45 * FastMath.DEG_TO_RAD);
        spot.setSpotInnerAngle(5 * FastMath.DEG_TO_RAD);
        spot.setPosition(player.getWorldTranslation());
        spot.setDirection(player.getWorldTranslation().mult(-1));
        //spot.setColor(ColorRGBA.Blue); //Doesn't work?!
        rootNode.addLight(spot);


        //Background planet:
        Sphere planet2 = new Sphere(100, 100, 10f);
        planet2.setTextureMode(Sphere.TextureMode.Projected);

        Material m = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        m.setBoolean("UseMaterialColors",true);
        m.setColor("Diffuse", ColorRGBA.White);
        m.setFloat("Shininess", 64f);  // [0,128]


        m.setTexture("DiffuseMap",
                assetManager.loadTexture("Textures/sun.jpg"));
        Geometry gp2 = new Geometry("p2", planet2);
        gp2.setMaterial(m);
        gp2.move(-20, 0, 10);
        rootNode.attachChild(gp2);



        //Point blob
        //Sphere sp = new Sphere(100, 100, 0.6f);
        Box sp = new Box(0.1f, 0.2f, 0.15f);
        blob = new Geometry("blob", sp);
        Material mat3 = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        mat3.setBoolean("UseMaterialColors",true);
        mat3.setColor("Diffuse",ColorRGBA.Red);
        mat3.setColor("Specular",ColorRGBA.Red);
        mat3.setFloat("Shininess", 64f);  // [0,128]
        blob.setMaterial(mat3);
        blob.move(0, 3.4f, 0);
        //blob.scale(2, 1.4f, 1);
        blob.addControl(new BallControl(player, this));
        Node blobPivotNode = new Node();
        blobPivotNode.attachChild(blob);
        rootNode.attachChild(blobPivotNode);

        Sphere planet3 = new Sphere(30, 30, 0.1f);
        Geometry gp3 = new Geometry("p3", planet3);
        gp3.setMaterial(mat);
        gp3.move(0, 0, 1);
        blobPivotNode.attachChild(gp3);

        Sphere planet4 = new Sphere(30, 30, 0.05f);
        Geometry gp4 = new Geometry("p4", planet4);
        gp4.setMaterial(mat);
        gp4.move(0, 0.5f, 0);
        blobPivotNode.attachChild(gp4);



        //Camera:
        Node node2 = new Node();
        CameraNode camNode = new CameraNode("CamNode", cam);
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(0, 0, -8f);
        node2.attachChild(camNode);
        node2.rotate(FastMath.HALF_PI + -8*FastMath.DEG_TO_RAD,
                FastMath.PI,
                0);
        //PRESS C TO GET CAMERA INFO FOR SETTING CHASECAM!

        //Player rotation:
        Node playerPivot = new Node("pivot");
        playerPivot.attachChild(player);
        playerPivot.rotate(FastMath.PI/2, 0, 0);
        playerPivot.attachChild(node2);     //Adds the FPV.
        playerPivot.addLight(spot);
        rootNode.attachChild(playerPivot);



        //Planet:
        Planet planet = new Planet(assetManager);
        rootNode.attachChild(planet);

        //myPlanet.setCullHint(Spatial.CullHint.Always);

        //Sunlight:
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(2,0,-1).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);


        //rootNode.move(0, 0, -1);
        initKeys();

        happy = new AudioNode(assetManager, "Sounds/happy_1.WAV", AudioData.DataType.Buffer);
        happy.setLooping(true);  // activate continuous playing
        happy.setPositional(true);
        happy.setVolume(1);
        rootNode.attachChild(happy);
        happy.play(); // play continuously!

        //playerPivot.addLight(spot);

        spot.setPosition(player.getWorldTranslation());
        spot.setDirection(player.getWorldTranslation().mult(-1));

        for (int i = 0; i < 50; i++) {                   //TODO Replace with planet.populate();
            createBlob();
        }


    }

    public void add(Geometry geom) {
        rootNode.attachChild(geom);
    }

    public void remove(Geometry geom) {
        rootNode.detachChild(geom);
    }

    public Geometry makeLine(Vector3f from, Vector3f to, ColorRGBA color) {
        //LIne
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);

        Mesh lineMesh = new Mesh();
        lineMesh.setMode(Mesh.Mode.Lines);
        lineMesh.setBuffer(VertexBuffer.Type.Position, 3,
                new float[]{ from.x, from.y, from.z, to.x, to.y, to.z});
        lineMesh.setBuffer(VertexBuffer.Type.Index, 2, new short[]{ 0, 1 });
        //lineMesh.updateBound();
        //lineMesh.updateCounts();
        Geometry lineGeometry = new Geometry("line", lineMesh);
        //Material lineMaterial = getAssetManager().loadMaterial("red_color.j3m");
        lineGeometry.setMaterial(mat);
        return lineGeometry;
    }

    private void createBlob() {
        Box sp = new Box(0.1f, 0.2f, 0.15f);
        Geometry blob = new Geometry("blob", sp);
        Material mat3 = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        mat3.setBoolean("UseMaterialColors",true);
        mat3.setColor("Diffuse",ColorRGBA.Red);
        mat3.setColor("Specular",ColorRGBA.Red);
        mat3.setFloat("Shininess", 64f);  // [0,128]
        blob.setMaterial(mat3);
        blob.move(0, 3.4f, 0);
        //blob.scale(2, 1.4f, 1);
        blob.addControl(new BallControl(player, this));
        Node blobPivotNode = new Node();
        blobPivotNode.attachChild(blob);
        blobPivotNode.rotate(FastMath.rand.nextInt(360)*FastMath.DEG_TO_RAD,
                FastMath.rand.nextInt(360)*FastMath.DEG_TO_RAD,
                FastMath.rand.nextInt(360)*FastMath.DEG_TO_RAD);
        rootNode.attachChild(blobPivotNode);
    }

    /** Custom Keybinding: Map named actions to inputs. */
    private void initKeys() {
        // You can map one or several inputs to one named action
        inputManager.addMapping("forwards",  new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("left",   new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("right",  new KeyTrigger(KeyInput.KEY_L));
        inputManager.addMapping("backwards", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("rotateLeft", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("rotateRight", new KeyTrigger(KeyInput.KEY_V));
        inputManager.addMapping("beam", new KeyTrigger(KeyInput.KEY_SPACE));

        // Add the names to the action listener.
        inputManager.addListener(analogListener,
                "forwards","left","right","backwards",
                "rotateLeft", "rotateRight", "beam");

    }

    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            Node pivot = (Node) rootNode.getChild("pivot");
            if (name.equals("forwards")) {
                pivot.rotate(-1*tpf, 0, 0);
            }
            if (name.equals("left")) {
                pivot.rotate(0, 0, 1*tpf);
            }
            if (name.equals("right")) {
                pivot.rotate(0, 0, -1*tpf);
            }
            if (name.equals("backwards")) {
                pivot.rotate(1*tpf, 0, 0);
            }
            if (name.equals("rotateLeft")) {
                pivot.rotate(0, 2*tpf, 0);
            }
            if (name.equals("rotateRight")) {
                pivot.rotate(0, -2*tpf, 0);
            }
            if (name.equals("beam")) {
                blob.removeControl(BallControl.class);
                Vector3f blobPos = blob.getWorldTranslation();
                Vector3f myPos = player.getWorldTranslation();
                Vector3f blobToPlayerVector = myPos.subtract(blobPos).normalize();
                blob.move(Vector3f.UNIT_Y.mult(0.01f));

            }

            if (rootNode.hasChild((Geometry) rootNode.getChild("blob"))) {
                Geometry blob = (Geometry) rootNode.getChild("blob");
                Vector3f blobPos = blob.getWorldTranslation();
                Vector3f myPos = player.getWorldTranslation();

                if (myPos.subtract(blobPos).length() < 0.8f) {
                    AudioNode pointSound = new AudioNode(
                            assetManager, "Sound/Effects/Gun.wav",
                            AudioData.DataType.Buffer);
                    pointSound.setPositional(false);
                    pointSound.setLooping(false);
                    pointSound.setVolume(2);
                    rootNode.attachChild(pointSound);
                    pointSound.playInstance();

                    rootNode.detachChild(blob.getParent());
                }

                //blob.setCullHint(Spatial.CullHint.Always);
            }

            spot.setPosition(player.getWorldTranslation());
            spot.setDirection(player.getWorldTranslation().mult(-1));
        }
    };

    @Override
    public void simpleUpdate(float tpf) {

        //listener.setLocation(player.getLocalTranslation());
        //listener.setRotation(player.getWorldRotation());
        if (blob.getWorldTranslation().distance(Vector3f.ZERO) > 3.4f) {
            blob.move(blob.getLocalTranslation().mult(-1).normalize().mult(0.001f));
        }

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
