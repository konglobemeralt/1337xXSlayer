package com.projectdgdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.Config;
import javafx.util.Pair;
<<<<<<< HEAD

=======
>>>>>>> b5f9f7768f194a14f44ed4bbb6d3ad5a9e3b9527

import java.util.Collection;

/**
 * Created by konglobemeralt on 2017-05-07.
 *
 * Class handeling rendering graphical objects to the screen
 * and various graphical special effects.
 */
public class RenderManager {

    private ModelBatch modelBatch;
    private ModelBatch shadowBatch;

    private FPSLogger fps;

    public Collection<Pair<ModelInstance, btCollisionObject>> instances;

    public Environment environment;
    DirectionalShadowLight shadowLight;
    public Shader shader;


    public void render (PerspectiveCamera cam, Collection<Pair<ModelInstance, btCollisionObject>> instances) {
        this.instances = instances;
        fps.log();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));


            if(Config.SHADOWMAPPING_ENABLED) {
                renderShadowMap(cam);
            }
        renderToScreen(cam);

    }

    /**
     * renderToScreen renders graphical objects to screen
     *
     * @param cam PerspectiveCam the current camera
     */

    private void renderToScreen(PerspectiveCamera cam){
        modelBatch.begin(cam);
        for (Pair<ModelInstance, btCollisionObject> instance : instances) {
            modelBatch.render(instance.getKey(), environment);
        }
        modelBatch.end();
    }

    /**
     * renderShadowMap creates a shadow map to be used by renderToScreen
     *
     * @param cam PerspectiveCam the current camera
     */

    private void renderShadowMap(PerspectiveCamera cam){
        shadowLight.begin(Vector3.Zero, cam.direction);
        shadowBatch.begin(shadowLight.getCamera());

        for (Pair<ModelInstance, btCollisionObject> instance : instances) {
            shadowBatch.render(instance.getKey(), environment);
        }
        shadowBatch.end();
        shadowLight.end();
    }

    /**
     * createBatches creates the batches for rendering
     *
     */
    public void createBatches(){
        modelBatch = new ModelBatch();
        shadowBatch = new ModelBatch(new DepthShaderProvider());
    }

    /**
     * createEnvironment creates the enviroment
     *
     */
    public void createEnvironment(){
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,
                Config.AMBIENT_LIGHT_R,
                Config.AMBIENT_LIGHT_G,
                Config.AMBIENT_LIGHT_B,
                Config.AMBIENT_LIGHT_A));
        if(Config.SHADOWMAPPING_ENABLED){
            environment.add((shadowLight = new DirectionalShadowLight(Config.SHADOW_MAP_WIDTH,
                    Config.SHADOW_MAP_HEIGHT,
                    Config.SHADOW_MAP_VIEWPORT_WIDTH,
                    Config.SHADOW_MAP_VIEWPORT_HEIGHT,
                    Config.SHADOW_MAP_NEAR,
                    Config.SHADOW_MAP_FAR)).set(
                    Config.SUN_LIGHT_R/100f,
                    Config.SUN_LIGHT_G/100f,
                    Config.SUN_LIGHT_B/100f,
                    Config.SUN_LIGHT_X,
                    Config.SUN_LIGHT_Y,
                    Config.SUN_LIGHT_Z));
            environment.shadowMap = shadowLight;
        }else{
            environment.add(new DirectionalLight().set(Config.SUN_LIGHT_R/100f,
                    Config.SUN_LIGHT_G/100f,
                    Config.SUN_LIGHT_B/100f,
                    Config.SUN_LIGHT_X,
                    Config.SUN_LIGHT_Y,
                    Config.SUN_LIGHT_Z));
        }
        environment.add(new PointLight().set(0.9f, 0.3f, 0.3f,
                35, 15f, 45f, 100f));

    }

    public void init(){
        createEnvironment();
        createBatches();

        createShaders();

        fps = new FPSLogger();
    }

    private void createShaders(){
        shader = new BaseShader();
        shader.init();
    }

    public void dispose () {
        modelBatch.dispose();
        shadowBatch.dispose();
        instances.clear();
    }


}
