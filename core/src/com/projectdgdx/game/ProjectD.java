package com.projectdgdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ProjectD extends ApplicationAdapter {
	private PerspectiveCamera camera;
	public CameraInputController camController;
	private Model model;
	private ModelInstance modelInstance;
	private Environment environment;

	private Texture img;
	private String vertexShader;
	private String fragmentShader;
	private ShaderProgram shaderProgram;
	private RenderContext renderContext;
	private Shader shader;
	private Renderable renderable;

	private ModelBatch modelBatch;

	//Uniforms
	int u_projTrans;
	int u_worldTrans;

	@Override
	public void create () {
		createSceneCamera();
		createModels();
		createEnviroment();
		createShaders();

		NodePart root = model.nodes.get(0).parts.get(0);

		renderable = new Renderable();
		root.setRenderable(renderable);
		renderable.environment = environment;
		renderable.worldTransform.idt();

		renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
		shader = new DefaultShader(renderable);
		shader.init();
	}

	private void createShaders(){
		vertexShader = Gdx.files.internal("shaders/vertexShader.glsl").readString();
		fragmentShader = Gdx.files.internal("shaders/fragmentShader.glsl").readString();
		shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
		if (!shaderProgram.isCompiled())
			throw new GdxRuntimeException(shaderProgram.getLog());

		u_projTrans = shaderProgram.getUniformLocation("u_projViewTrans");
		u_worldTrans = shaderProgram.getUniformLocation("u_worldTrans");
	}

	private void createSceneCamera(){
		camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0f, 0f, 3f);
		camera.lookAt(0f, 0f, 0f);
		camera.near = 0.01f;
		camera.far = 1000f;
		camController = new CameraInputController(camera);
		Gdx.input.setInputProcessor(camController);
	}



	private void createModels(){
		//ModelLoader loader = new ObjLoader();
		//model = loader.loadModel(Gdx.files.internal("bunny.obj"));

		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(2f, 2f, 2f,
								new Material(ColorAttribute.createDiffuse(Color.BLUE)),
								VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		modelInstance = new ModelInstance(model);

		modelBatch = new ModelBatch(shaderProgram);
		modelBatch.
	}

	private void createEnviroment(){
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f));
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

	}

	@Override
	public void render () {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		camera.rotateAround(Vector3.Zero, new Vector3(0,1,0),1f);
		camera.update();
		renderContext.begin();
		shaderProgram.begin();
		//shader.begin(camera, renderContext);
		//shader.render(renderable);
		//shader.end();
		renderContext.end();
	}

	@Override
	public void dispose () {
		shader.dispose();
		model.dispose();
	}
}
