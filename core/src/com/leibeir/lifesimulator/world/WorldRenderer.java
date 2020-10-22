package com.leibeir.lifesimulator.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import com.leibeir.lifesimulator.api.IRenderer;

public abstract class WorldRenderer implements IRenderer {
    public final World world;
    protected final ModelBatch modelBatch = new ModelBatch();
    protected final Array<ModelInstance> modelInstances = new Array<>();

    public final static Color grass = new Color(0x5a691400);
    public final static Color sand = new Color(0x80808000);
    public final static Color deepSand = new Color(0x65432100);

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
