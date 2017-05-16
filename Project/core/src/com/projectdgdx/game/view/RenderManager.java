package com.projectdgdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.controller.GameObjectContainer;
import com.projectdgdx.game.model.Character;
import com.projectdgdx.game.model.Spotlight;
import com.projectdgdx.game.utils.Vector3d;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

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

    private float lifeTime;

    public Collection<GameObjectContainer> instances;

    //TODO: MERGE WITH SPOT OBJECT TO CREATE VIABLE OBJECT
    private List<PointLight> pointLightList =  new ArrayList();
    private List<Vector3d> discoLightPosList =  new ArrayList();
    private List<PointLight> discoLightList =  new ArrayList();

    public Environment environment;
    DirectionalShadowLight shadowLight;
    public Shader shader;

    Random rand;

    public void render (PerspectiveCamera cam, List<Spotlight>lights, Collection<GameObjectContainer> instances) {
        fps.log();

        handleLights(lights);


        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));


        if(Config.SHADOWMAPPING_ENABLED) {
            renderShadowMap(cam, instances);
        }
        renderToScreen(cam, instances);

    }


    private void handleLights(List<Spotlight>lights){

        updatePointLights(lights);

        if(Config.DISCO_FACTOR > 0){
            updateDiscoLights();
        }


    }


    /**
     * renderToScreen renders graphical objects to screen
     *
     * @param cam PerspectiveCam the current camera
     */

    private void renderToScreen(PerspectiveCamera cam, Collection<GameObjectContainer> instances){
        modelBatch.begin(cam);
        for (GameObjectContainer instance : instances) {
            modelBatch.render(instance.getGraphicObject(), environment);

            if(Config.DEBUG) {
                ModelBuilder modelBuilder = new ModelBuilder();
                Vector3 dimensions = instance.getGraphicObject().calculateBoundingBox(new BoundingBox()).getDimensions(new Vector3());

                Model model;
                if(instance.getGameObject() instanceof Character) {
                    model = modelBuilder.createCylinder(2, 5, 2,20,
                            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                }else {
                    model = modelBuilder.createBox(dimensions.x, dimensions.y, dimensions.z,
                            new Material(ColorAttribute.createDiffuse(Color.RED)),
                            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                }

                ModelInstance modelInstance = new ModelInstance(model);
                modelInstance.transform = instance.getGraphicObject().transform;
                modelBatch.render(modelInstance, environment);

            }
        }
        modelBatch.end();
    }

    /**
     * renderShadowMap creates a shadow map to be used by renderToScreen
     *
     * @param cam PerspectiveCam the current camera
     */

    private void renderShadowMap(PerspectiveCamera cam, Collection<GameObjectContainer> instances){
        shadowLight.begin(Vector3.Zero, cam.direction);
        shadowBatch.begin(shadowLight.getCamera());

        for (GameObjectContainer instance : instances) {
            shadowBatch.render(instance.getGraphicObject(), environment);
        }
        shadowBatch.end();
        shadowLight.end();
    }

    /**
     * createBatches creates the batches for rendering
     *
     */
    public void createBatches(int lightCount){

        DefaultShader.Config config = new DefaultShader.Config();
        config.numDirectionalLights = 1;
        config.numPointLights =  lightCount + Config.DISCO_FACTOR;
        config.numSpotLights = 0;

        ShaderProvider shaderProvider = new DefaultShaderProvider(config);

        modelBatch = new ModelBatch(shaderProvider);
        //modelBatch = new ModelBatch();
        shadowBatch = new ModelBatch(new DepthShaderProvider());
    }

    /**
     * createEnvironment creates the enviroment
     * @param lights SpotLight
     */
    public void updatePointLights(List<Spotlight>lights){
        for (PointLight p: pointLightList) {
            environment.remove(p);
        }
        createPointLights(lights);
    }


    public void createPointLights(List<Spotlight>lights){
        for (Spotlight spot: lights) {
            PointLight light = new PointLight().set(spot.getColor().x, spot.getColor().y, spot.getColor().z,
                    spot.getPosition().x , spot.getPosition().y,  spot.getPosition().z, spot.getIntensity());
            environment.add(light);
            pointLightList.add(light);
            //renderSpotPos = spot.getPosition();
        }

    }

    //*******

    public void updateDiscoLights(){
        for(PointLight p: discoLightList){
            environment.remove(p);
            //pointLightList.remove(p);
        }
        moveDiscoLights();
        //createLights();
    }

    public void createDiscoLights(){
        for(int i = 0; i < Config.DISCO_FACTOR; i++){
            Vector3d pos = new Vector3d(rand.nextInt(300)-100, rand.nextInt(20)-5, rand.nextInt(300)-100);
            PointLight light = new PointLight().set(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(),
                    pos.x , pos.y,  pos.z, 70f);
            environment.add(light);
            discoLightList.add(light);
            discoLightPosList.add(pos);

        }

    }


    public void moveDiscoLights(){
        for(int i = 0; i < Config.DISCO_FACTOR; i++){
            discoLightPosList.get(i).x += rand.nextFloat() * (2f + 2f) + -2f;
            discoLightPosList.get(i).y += rand.nextFloat() * (0.5f +0.5f) + -0.5f;
            discoLightPosList.get(i).z += rand.nextFloat() * (2f + 2f) + -2f;

            PointLight light = new PointLight().set(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(),
                    discoLightPosList.get(i).x , discoLightPosList.get(i).y,  discoLightPosList.get(i).z, 50f);
            environment.add(light);
            pointLightList.add(light);
        }

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


    }

    public void init(List<Spotlight>lights){
        rand = new Random();
        createEnvironment();
        System.out.println("LightSize >>" + lights.size());
        createBatches(lights.size());
        createDiscoLights();
        createPointLights(lights);
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
        environment.clear();
        pointLightList.clear();
    }



}