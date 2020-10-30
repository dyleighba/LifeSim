package com.leibeir.lifesimulator.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.leibeir.lifesimulator.api.IWorld;
import com.leibeir.lifesimulator.api.tile.TileType;
import com.leibeir.lifesimulator.logic.data.Simulation;
import com.leibeir.lifesimulator.logic.data.Terrain;
import com.leibeir.lifesimulator.render.*;

public class World implements IWorld {
    private final int size;
    private final Renderer renderer;
    private final WaterRenderer waterRenderer;
    private final PhysicalsRenderer physicalsRenderer;
    private final ItemRenderer itemRenderer;
    private final Simulation simulation;

    public World(int size) {
        this.size = size;
        simulation = new Simulation(this.size);
        renderer = new TerrainMeshRenderer(simulation.terrain);
        waterRenderer = new WaterRenderer(simulation.terrain);
        physicalsRenderer = new PhysicalsRenderer(simulation.terrain);
        itemRenderer = new ItemRenderer(simulation);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void render(Camera camera, Environment environment) {
        renderer.render(camera, environment);
        waterRenderer.render(camera, environment);
        physicalsRenderer.render(camera, environment);
        itemRenderer.render(camera, environment);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        waterRenderer.dispose();
        physicalsRenderer.dispose();
        itemRenderer.dispose();
    }
}
