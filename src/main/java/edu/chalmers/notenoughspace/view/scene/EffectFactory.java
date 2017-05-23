package edu.chalmers.notenoughspace.view.scene;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import edu.chalmers.notenoughspace.event.Bus;

/**
 * Created by Vibergf on 18/05/2017.
 */
class EffectFactory {

    private static int idCounter = 0;

    private EffectFactory(){
    }

    static ParticleEmitter createEffect(AssetManager assetManager, String id){
        ParticleEmitter emitter = null;
        if(id.equals("satelliteExplosion")){
            emitter = new ParticleEmitter("" + idCounter++, ParticleMesh.Type.Triangle, 1);
            Material mat_red = new Material(assetManager,
                    "Common/MatDefs/Misc/Particle.j3md");
            mat_red.setTexture("Texture", assetManager.loadTexture(
                    "Effects/shockwave.png"));
            emitter.setMaterial(mat_red);
            emitter.setImagesX(1);
            emitter.setImagesY(1); // 2x2 texture animation
            emitter.setStartColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
            emitter.setEndColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
            emitter.setStartSize(0.1f);
            emitter.setEndSize(0.7f);
            emitter.setGravity(0, 0, 0);
            emitter.setLowLife(0.5f);
            emitter.setHighLife(1f);
            emitter.setParticlesPerSec(0.1f);
            emitter.addControl(new SingleParticleControl());
        }else if(id.equals("goldGlitter")){
            emitter = new ParticleEmitter("" + idCounter++, ParticleMesh.Type.Triangle, 10);
            Material mat_red = new Material(assetManager,
                    "Common/MatDefs/Misc/Particle.j3md");
            mat_red.setTexture("Texture", assetManager.loadTexture(
                    "Effects/spark.png"));
            emitter.setMaterial(mat_red);
            emitter.setImagesX(2);
            emitter.setImagesY(2); // 2x2 texture animation
            emitter.setStartColor(  new ColorRGBA(1f, 1f, 0f, 1f));   // red
            emitter.setEndColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
            emitter.setStartSize(0.1f);
            emitter.setEndSize(0.02f);
            emitter.setGravity(0, 0, 0);
            emitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0.6f, 0));
            emitter.getParticleInfluencer().setVelocityVariation(.60f);
            emitter.setLowLife(0.6f);
            emitter.setHighLife(1.2f);
            emitter.setParticlesPerSec(8f);
        }
        return emitter;
    }

    private static class SingleParticleControl extends AbstractControl{

        boolean done = false;

        protected void controlUpdate(float v) {
            if(done && ((ParticleEmitter) spatial).getNumVisibleParticles() == 0){
                spatial.removeFromParent();
                spatial.removeControl(SingleParticleControl.class);
            }else{
                ((ParticleEmitter) spatial).emitAllParticles();
                done = true;
            }
        }

        protected void controlRender(RenderManager renderManager, ViewPort viewPort) {}
    }
}
