package com.leibeir.lifesimulator.render;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import com.leibeir.lifesimulator.api.IRenderer;

public abstract class Renderer implements IRenderer {
    protected ModelBatch modelBatch = new ModelBatch();
    protected Array<ModelInstance> modelInstances = new Array<>();
    protected AssetManager assetManager = Assets.getAssetManager();

    protected boolean loadingAssets = false;

    public abstract void update();

    public void render(Camera camera, Environment environment) {
        if (loadingAssets && assetManager.update()) {
            loadingAssets = false;
            doneAssetLoading();
        }
        modelBatch.begin(camera);
        modelBatch.render(modelInstances, environment);
        modelBatch.end();
    }

    public void dispose() {
        modelBatch.dispose();
        modelInstances.clear();
    }

    protected void doneAssetLoading() {}

    public <T> void loadAsset (String fileName, Class<T> type) {
        assetManager.load(fileName, type);
        loadingAssets = true;
    }
}
