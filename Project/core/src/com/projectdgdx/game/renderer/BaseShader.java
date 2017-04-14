package com.projectdgdx.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by konglobemeralt on 2017-04-05.
 */
public class BaseShader implements Shader {

    ShaderProgram shader;
    Camera camera;
    RenderContext context;

    //Uniforms
    int u_projViewTrans;
    int u_worldTrans;
    int u_color;
    int u_texture;
    int[] u_bones = new int [3];


    @Override
    public void init () {
        String vert = Gdx.files.internal("shaders/vertexShader.glsl").readString();
        String frag = Gdx.files.internal("shaders/fragmentShader.glsl").readString();
        shader = new ShaderProgram(vert, frag);
        if (!shader.isCompiled())
            throw new GdxRuntimeException(shader.getLog());

        u_projViewTrans = shader.getUniformLocation("u_projViewTrans");
        u_worldTrans = shader.getUniformLocation("u_worldTrans");
        u_color = shader.getUniformLocation("u_color");
        u_texture = shader.getUniformLocation("u_texture");

        for(int i = 0; i< 3; i++){
            u_bones[i] = shader.getUniformLocation("u_bones[" + i +"]");
        }

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
        //shader.setUniformf(u_color, MathUtils.random(), MathUtils.random(), MathUtils.random());

        Texture tex = ((TextureAttribute)renderable.material.get(TextureAttribute.Diffuse)).textureDescription.texture;

        shader.setUniformi(u_texture, context.textureBinder.bind(tex));
        float[] bones = new float[renderable.bones.length];

        Matrix4 idtMatrix = new Matrix4().idt(); //Identity matrix

        for (int i = 0; i < bones.length; i++) {
            bones[i] = idtMatrix.val[i%16];
        }

        for (int i = 0; i < bones.length; i++) {
            final int idx = i/16;
            bones[i] = (renderable.bones == null || idx >= renderable.bones.length || renderable.bones[idx] == null) ?
                    idtMatrix.val[i%16] : renderable.bones[idx].val[i%16];
        }

        shader.setUniformMatrix4fv("u_bones", bones, 0, renderable.bones.length); //Nor is the replacement of "u_bones" to u_bones (program.getUniformLocation("u_bones")) solving the issue.
        renderable.meshPart.render(shader);

    }

    @Override
    public void end () {  shader.end();  }
    @Override
    public int compareTo (Shader other) {
        return 0;
    }
    @Override
    public boolean canRender (Renderable instance) {
        return true;
    }
}