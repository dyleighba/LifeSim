package com.leibeir.lifesimulator.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.leibeir.lifesimulator.api.tile.TileType;
import com.leibeir.lifesimulator.logic.data.Terrain;
import com.leibeir.lifesimulator.logic.terrain.TerrainGeneration;
import com.leibeir.lifesimulator.util.RandomColour;
import com.leibeir.lifesimulator.world.World;

public class TerrainRenderer extends Renderer {
    private final Model model;
    private final Terrain terrain;

    public TerrainRenderer(Terrain terrain) {
        this.terrain = terrain;
        // create cube
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(1f, 1f, 1f,
                new Material(ColorAttribute.createDiffuse(Color.MAGENTA)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        for (int x=0; x<terrain.getSize(); x++) {
            for (int z=0; z<terrain.getSize(); z++){
                ModelInstance modelInstance = new ModelInstance(model);
                modelInstances.add(modelInstance);
            }
        }
        update();
    }

    @Override
    public void update() {
        for (int x=0; x<terrain.getSize(); x++) {
            for (int z=0; z<terrain.getSize(); z++){
                float elevation = terrain.getElevation(x, z);
                TileType type = terrain.getType(x, z);
                if (type == TileType.Water) type = TerrainGeneration.SHORE_TYPE;
                if (type == TileType.DeepWater) type = TileType.Dirt;
                Color colour = RandomColour.noisyColour(TileColour.get(type));
                int pos = (x*terrain.getSize()) + z;
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
