package com.leibeir.lifesimulator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.leibeir.lifesimulator.util.MathHelper;
import com.leibeir.lifesimulator.util.RandomColour;
import com.leibeir.lifesimulator.world.World;

import java.util.Random;

public class LifeSimulator implements ApplicationListener {
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
		cam.position.set(32f, 7f, 32f);
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
		assets.load("Trees.g3db", Model.class);
		loading = true;
	}

	private void doneLoading() {
		Model ship = assets.get("Trees.g3db", Model.class);
		Random rand = new Random();
		for (int x=0; x <= world.getSize()-1; x+=7) {
			for (int z=0; z <= world.getSize()-1; z+=7) {
				if (world.isGrass(x, z)){
					String model_id = ship.nodes.get(rand.nextInt(ship.nodes.size)).id;
					if (model_id.contains("Stump")) {
						if (rand.nextInt(32) > 2) {
							continue;
						}
					}
					ModelInstance shipInstance = new ModelInstance(ship, model_id);
					//shipInstance.materials.get(0).set(new ColorAttribute(ColorAttribute.Diffuse, Color.BROWN));
					//shipInstance.materials.get(0).set(ColorAttribute.createDiffuse(RandomColour.get()));
					//Material randMat = randomColourMat.getRandMat();
					//Gdx.app.log("RandMat", randMat.toString());
					//shipInstance.materials.set(0, randMat);
					float new_x = (x-2)+rand.nextInt(8);
					new_x = MathHelper.minmax(0, world.getSize()-1, new_x);
					float new_z = (z-2)+rand.nextInt(8);
					new_z = MathHelper.minmax(0, world.getSize()-1, new_z);

					if (world.isGrass((int)new_x, (int)new_z)) {
						shipInstance.transform.setToTranslation(
								new_x, world.getElevation((int)new_x, (int)new_z)-0.1f, new_z);
						humanInstances.add(shipInstance);
					}
				}
			}
		}
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
