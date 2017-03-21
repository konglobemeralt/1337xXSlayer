package com.projectdgdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class ProjectD extends ApplicationAdapter {
	private PerspectiveCamera camera;
	public CameraInputController camController;
	private ModelBatch modelBatch;
	private Model box;
	private ModelInstance boxInstance;
	private Environment environment;
	
	@Override
	public void create () {
		createSceneCamera();
		createModels();
		createEnviroment();
		for (Controller controller : Controllers.getControllers()) {
			System.out.println(controller.getName());
		}
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
		modelBatch = new ModelBatch();
		ModelBuilder modelBuilder = new ModelBuilder();
		box = modelBuilder.createBox(2f, 2f, 2f,
				new Material(ColorAttribute.createDiffuse(Color.BLUE)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		boxInstance = new ModelInstance(box, 0, 0, 0);
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

		modelBatch.begin(camera);
		modelBatch.render(boxInstance, environment);
		modelBatch.end();

	}

	public void update() {

	}
	
	@Override
	public void dispose () {
		modelBatch.dispose();
		box.dispose();
	}
}
