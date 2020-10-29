package com.leibeir.lifesimulator.render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import com.leibeir.lifesimulator.api.IRenderer;

public abstract class Renderer implements IRenderer {
    protected ModelBatch modelBatch = new ModelBatch();
    protected Array<ModelInstance> modelInstances = new Array<>();

    public void render(Camera camera, Environment environment) {
        modelBatch.begin(camera);
        modelBatch.render(modelInstances, environment);
        modelBatch.end();
    }

    public void dispose() {
        modelBatch.dispose();
        modelInstances.clear();
    }
}
