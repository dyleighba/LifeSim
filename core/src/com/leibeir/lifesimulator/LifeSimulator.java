package com.leibeir.lifesimulator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.leibeir.lifesimulator.util.MathHelper;
import com.leibeir.lifesimulator.util.RandomColour;
import com.leibeir.lifesimulator.world.World;

import java.util.Random;

public class LifeSimulator implements ApplicationListener {
	public static String modelFile = "AppReady.g3db";

	public PerspectiveCamera cam;
	public CameraInputController camController;
	public ModelBatch modelBatch;
	public AssetManager assets;
	public Array<ModelInstance> humanInstances = new Array<>();
	public Environment environment;
	public boolean loading;
	public World world;

	@Override
	public void create () {
		Gdx.gl.glDisable(GL20.GL_BLEND);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		world = new World(128);
		Vector3 worldCenter = new Vector3((float)world.getSize()/2, 0, (float)world.getSize()/2);
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(1, 1, 1, -1f, -0.8f, -0.2f));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 7f, 0f);
		cam.lookAt(worldCenter);
		cam.near = 0.1f;
		cam.far = 500f;
		cam.update();

		camController = new CameraInputController(cam);
		camController.rotateLeftKey = -1;
		camController.rotateRightKey = -1;
		//camController.target = worldCenter;
		Gdx.input.setInputProcessor(camController);

		assets = new AssetManager();
		assets.load(modelFile, Model.class);
		loading = true;
	}

	private void doneLoading() {
		Model physicalModels = assets.get(modelFile, Model.class);
		// TODO pass the model to PhysicalsRenderer
		world.setupPhysicalsRenderer(physicalModels);
		loading = false;
	}

	@Override
	public void render () {
		if (loading && assets.update())
			doneLoading();
		camController.target = cam.position;
		camController.update();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0.62f,0.816f,0.902f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		world.render(cam, environment);

		modelBatch.begin(cam);
		modelBatch.render(humanInstances, environment);
		modelBatch.end();
	}

	@Override
	public void dispose () {
		modelBatch.dispose();
		humanInstances.clear();
		assets.dispose();
		world.dispose();
	}

	public void resume () {
	}

	public void resize (int width, int height) {
	}

	public void pause () {
	}
}
