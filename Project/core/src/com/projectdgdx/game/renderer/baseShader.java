package com.projectdgdx.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by konglobemeralt on 2017-04-05.
 */
public class BaseShader implements Shader {
    ShaderProgram shader;
    Camera camera;
    RenderContext context;

    @Override
    public void init () {
        String vert = Gdx.files.internal("shaders/vertexShader.glsl").readString();
        String frag = Gdx.files.internal("shaders/fragmentShader.glsl").readString();
        shader = new ShaderProgram(vert, frag);
        if (!shader.isCompiled())
            throw new GdxRuntimeException(shader.getLog());
    }

    @Override
    public void dispose () {
        shader.dispose();
    }

    @Override
    public void begin (Camera camera, RenderContext context) {
        this.camera = camera;
        this.context = context;
        shader.begin();
        shader.setUniformMatrix("u_projViewTrans", camera.combined);
        context.setDepthTest(GL20.GL_LEQUAL);
        context.setCullFace(GL20.GL_BACK);
    }

    @Override
    public void render (Renderable renderable) {
        shader.setUniformMatrix("u_worldTrans", renderable.worldTransform);
        renderable.meshPart.render(shader);
    }

    @Override
    public void end () {    }
    @Override
    public int compareTo (Shader other) {
        return 0;
    }
    @Override
    public boolean canRender (Renderable instance) {
        return true;
    }
}