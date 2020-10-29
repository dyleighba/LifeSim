package com.leibeir.lifesimulator.render;

import com.badlogic.gdx.Gdx;
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

public class WaterRenderer extends WorldRenderer {
    public final int waterGridSize;

    public final static Color water = new Color(0x82A7A6BB);
    private static final String modelFile = "Water.obj";

    public WaterRenderer(World world) {
        super(world);

        waterGridSize = (int)Math.ceil((float)world.getSize() / 16);
        loadAsset(modelFile, Model.class);
    }

    public void update() {
        modelInstances.clear();
        float seaLevel = world.getSeaLevel();
        for (int x=0; x<waterGridSize; x++) {
            for (int z=0; z<waterGridSize; z++) {
                ModelInstance mi = new ModelInstance(assetManager.get(modelFile, Model.class));
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
    }

    @Override
    public void render(Camera camera, Environment environment) {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        super.render(camera, environment);
    }

    @Override
    public void doneAssetLoading() {
        update();
    }

}
