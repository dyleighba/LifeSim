package com.leibeir.lifesimulator.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.leibeir.lifesimulator.world.World;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WaterRenderer extends WorldRenderer {
    private AssetManager assets;
    public final int waterGridSize;
    public final static Color water = new Color(0x82A7A6BB);

    private boolean loading;

    public WaterRenderer(World world) {
        super(world);

        assets = new AssetManager();
        assets.load("Water.obj", Model.class);
        loading = true;
        waterGridSize = (int)Math.ceil((float)world.getSize() / 16);
    }

    private void doneLoading() {
        float seaLevel = world.getSeaLevel();
        for (int x=0; x<waterGridSize; x++) {
            for (int z=0; z<waterGridSize; z++) {
                ModelInstance mi = new ModelInstance(assets.get("Water.obj", Model.class));
                mi.materials.get(0).set(ColorAttribute.createDiffuse(water), new BlendingAttribute(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA));
                float offset_x = (x*16)+7.5f;
                float offset_z = (z*16)+7.5f;

                // Hack to prevent flickers on edges
                if (x == 0) offset_x += 0.001f;
                if (x == waterGridSize-1) offset_x -= 0.001f;
                if (z == 0) offset_z += 0.001f;
                if (z == waterGridSize-1) offset_z -= 0.001f;

                mi.transform.set(new Vector3(offset_x, seaLevel-0.235f, offset_z), new Quaternion());
                modelInstances.add(mi);
            }
        }

        loading = false;
    }

    public void update() {
        throw new NotImplementedException();
    };

    public void render(Camera camera, Environment environment) {
        if (loading && assets.update())
            doneLoading();
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        super.render(camera, environment);
    }

}
