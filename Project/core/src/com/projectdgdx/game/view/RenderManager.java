package com.projectdgdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.model.GameObject;
import com.projectdgdx.game.utils.AssetManager;
import com.projectdgdx.game.utils.AssetsFinder;
import com.projectdgdx.game.utils.Map;
import com.projectdgdx.game.utils.VectorConverter;

import java.util.Random;

/**
 * Created by konglobemeralt on 2017-05-07.
 */
public class RenderManager {

    private ModelBatch modelBatch;
    private ModelBatch shadowBatch;

    private FPSLogger fps;

    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Array<AnimationController> animationControllers = new Array<AnimationController>();
    private AnimationController animController;

    public Environment environment;
    DirectionalShadowLight shadowLight;
    public boolean loading;
    public Shader shader;

    private Random rand;

    Map map;

    public void render (PerspectiveCamera cam, Array<ModelInstance> instances) {
        this.instances = instances;
        fps.log();

        if (loading && AssetManager.update())
            doneLoading();


        for(AnimationController controllerInstance: animationControllers){
            controllerInstance.update(Gdx.graphics.getDeltaTime() + rand.nextFloat() * 0.02f);
        }

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));


            if(Config.SHADOWMAPPING_ENABLED) {
                renderShadowMap(cam);
            }
        renderToScreen(cam);

    }

    private void renderToScreen(PerspectiveCamera cam){
        modelBatch.begin(cam);
        for (ModelInstance instance : instances) {
            modelBatch.render(instance, environment);
        }
        modelBatch.end();
    }

    private void renderShadowMap(PerspectiveCamera cam){
        shadowLight.begin(Vector3.Zero, cam.direction);
        shadowBatch.begin(shadowLight.getCamera());

        for (ModelInstance instance : instances) {
            shadowBatch.render(instance, environment);
        }
        shadowBatch.end();
        shadowLight.end();
    }

    public void loadAssets(){
        modelBatch = new ModelBatch();
        shadowBatch = new ModelBatch(new DepthShaderProvider());


        loading = true;
    }


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
                    Config.SUN_LIGHT_R,
                    Config.SUN_LIGHT_G,
                    Config.SUN_LIGHT_B,
                    Config.SUN_LIGHT_X,
                    Config.SUN_LIGHT_Y,
                    Config.SUN_LIGHT_Z));
            environment.shadowMap = shadowLight;
        }else{
            environment.add(new DirectionalLight().set(Config.SUN_LIGHT_R,
                    Config.SUN_LIGHT_G,
                    Config.SUN_LIGHT_B,
                    Config.SUN_LIGHT_X,
                    Config.SUN_LIGHT_Y,
                    Config.SUN_LIGHT_Z));
        }
        environment.add(new PointLight().set(0.9f, 0.3f, 0.3f,
                35, 15f, 45f, 100f));


    }


    private void doneLoading() {

        for (GameObject gameObject : map.getGameObjects()) {
            ModelInstance npcInstance;
            System.out.println(AssetsFinder.getModelPath(gameObject.getId()));
            npcInstance = new ModelInstance(AssetManager.getModel(AssetsFinder.getModelPath(gameObject.getId())));
            npcInstance.transform.setToTranslation(VectorConverter.convertToLibgdx(gameObject.getPosition()));
            Vector3 scale = VectorConverter.convertToLibgdx(gameObject.getScale());
            npcInstance.transform.scale(scale.x, scale.y, scale.z);
            Vector3 rotation = VectorConverter.convertToLibgdx(gameObject.getRotation());
            npcInstance.transform.rotate(Vector3.X, rotation.x);
            npcInstance.transform.rotate(Vector3.Y, rotation.y);
            npcInstance.transform.rotate(Vector3.Z, rotation.z);

            if(gameObject.getId() == "worker.basic" || gameObject.getId() == "player.basic") {
                animController = new AnimationController(npcInstance);
                animController.setAnimation("Robot|IdleAnim", -1, 0.2f, new AnimationController.AnimationListener() {
                    @Override
                    public void onEnd(AnimationController.AnimationDesc animation) {
                    }

                    @Override
                    public void onLoop(AnimationController.AnimationDesc animation) {
                        //   Gdx.app.log("INFO", "Animation Ended");
                    }
                });
                animationControllers.add(animController);
            }
            instances.add(npcInstance);
        }

        loading = false;

        shader = new BaseShader();
        shader.init();
    }

    public void init(Map map){
        rand = new Random();
        this.map = map;

        createEnvironment();
        loadAssets();

        shader = new BaseShader();
        shader.init();

        fps = new FPSLogger();
    }

    public void dispose () {
        modelBatch.dispose();
        instances.clear();
        AssetManager.dispose();
    }



}
