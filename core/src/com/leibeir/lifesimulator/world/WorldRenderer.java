package com.leibeir.lifesimulator.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import com.leibeir.lifesimulator.api.IRenderer;

public abstract class WorldRenderer implements IRenderer {
    protected final World world;
    protected final ModelBatch modelBatch = new ModelBatch();
    protected final Array<ModelInstance> modelInstances = new Array<>();

    public WorldRenderer(World world) {
        this.world = world;
    }

    @Override
    public void update() {};

    @Override
    public void render(Camera camera, Environment environment) {
        modelBatch.begin(camera);
        modelBatch.render(modelInstances, environment);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        modelInstances.clear();
    }
}
