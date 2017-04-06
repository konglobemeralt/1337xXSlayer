package com.projectdgdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.renderer.BaseShader;
import com.projectdgdx.game.utils.AssetManager;
import com.sun.xml.internal.xsom.impl.scd.Iterators;


public class ProjectD extends ApplicationAdapter {
    public PerspectiveCamera cam;
    public CameraInputController camController;
    public Shader shader;
    public RenderContext renderContext;
    public Model model;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public ModelBatch modelBatch;

    public Renderable renderable;

    @Override
    public void create () {
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(2f, 100f, 150f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 10000f;
        cam.update();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        AssetManager.loadModel("robo.g3dj");

        Model model = AssetManager.getRawModel("robo.g3dj");

        for (int x = -500; x <= 500; x+=60) {
            for (int z = -500; z<=500; z+=60) {
                instances.add(new ModelInstance(model, x, 0, z));
            }
        }

        NodePart blockPart = model.getNode("robo_root").getChild(0).parts.get(0);

        renderable = new Renderable();
        blockPart.setRenderable(renderable);
        renderable.environment = null;
        renderable.worldTransform.idt();

        renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
        shader = new BaseShader();
        shader.init();

        modelBatch = new ModelBatch();
    }

    @Override
    public void render () {
        camController.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        //renderable.meshPart.primitiveType = GL20.GL_LINE_STRIP;

        modelBatch.begin(cam);
        for (ModelInstance instance : instances)
            modelBatch.render(instance, shader);
        modelBatch.end();
    }

    @Override
    public void dispose () {
        AssetManager.dispose();
        modelBatch.dispose();
        shader.dispose();
        model.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}