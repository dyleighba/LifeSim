package com.leibeir.lifesimulator.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.leibeir.lifesimulator.util.RandomColour;

public class TerrainRenderer extends WorldRenderer {
    private final Model model;
    public final static Color grass = new Color(0x5a691400);
    public final static Color sand = new Color( 0x80808000);
    public final static Color deepSand = new Color( 0x65432100);

    public TerrainRenderer(World world) {
        super(world);

        // create cube
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(1f, 1f, 1f,
                new Material(ColorAttribute.createDiffuse(Color.MAGENTA)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        for (int x=0; x<world.getSize(); x++) {
            for (int z=0; z<world.getSize(); z++){
                ModelInstance modelInstance = new ModelInstance(model);
                modelInstances.add(modelInstance);
            }
        }
        update();
    }

    @Override
    public void update() {
        for (int x=0; x<world.getSize(); x++) {
            for (int z=0; z<world.getSize(); z++){
                float elevation = world.getElevation(x, z);
                Color colour = RandomColour.noisyColour(grass);
                //if (world.isWater(x, z)){
                //    colour = Color.BLUE;
                //}
                if (world.isSand(x, z) || world.isWater(x, z)){
                    colour = RandomColour.noisyColour(sand);
                }
                if (world.isDeepWater(x, z)) {
                    colour = RandomColour.noisyColour(deepSand);
                }
                int pos = (x*world.getSize()) + z;
                ModelInstance modelInstance = modelInstances.get(pos);
                modelInstance.materials.get(0).set(ColorAttribute.createDiffuse(colour));
                modelInstance.transform.setToTranslation(x, -5.5f+(elevation/2), z);
                modelInstance.transform.scale(1, 1+(elevation)+10,1);
                // Hack to hide water blocks while attempting new water renderer class
                //if (colour == Color.BLUE) modelInstance.transform.setToTranslation(0, -2000, 0);
            }
        }
    }
}
