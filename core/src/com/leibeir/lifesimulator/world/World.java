package com.leibeir.lifesimulator.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.leibeir.lifesimulator.api.IWorld;
import com.leibeir.lifesimulator.api.tile.TileType;
import com.leibeir.lifesimulator.logic.data.Terrain;
import com.leibeir.lifesimulator.render.PhysicalsRenderer;
import com.leibeir.lifesimulator.render.TerrainMeshRenderer;
import com.leibeir.lifesimulator.render.WaterRenderer;
import com.leibeir.lifesimulator.render.WorldRenderer;

public class World implements IWorld {
    private final int size;
    private final WorldRenderer renderer;
    private final WaterRenderer waterRenderer;
    private final PhysicalsRenderer physicalsRenderer;
    private final Terrain terrain;

    public World(int size){
        this.size = size;
        terrain = new Terrain(this.size);
        renderer = new TerrainMeshRenderer(this);
        waterRenderer = new WaterRenderer(this);
        physicalsRenderer = new PhysicalsRenderer(terrain);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public float getSeaLevel() {
        return terrain.getSeaLevel();
    }

    @Override
    public float getElevation(int x, int z) {
        return terrain.getElevation(x, z);
    }

    @Override
    public boolean isPassable(int x, int z) {
        return terrain.isPassable(x, z);
    }

    @Override
    public boolean isSand(int x, int z) {
        return terrain.getType(x, z) == TileType.Sand;
    }

    @Override
    public boolean isWater(int x, int z) {
        return terrain.getType(x, z) == TileType.Water;
    }

    @Override
    public boolean isDeepWater(int x, int z) {
        return terrain.getType(x, z) == TileType.DeepWater;
    }

    @Override
    public boolean isGrass(int x, int z) {
        return terrain.getType(x, z) == TileType.Grass;
    }

    @Override
    public boolean isStone(int x, int z) {
        return terrain.getType(x, z) == TileType.Stone;
    }

    @Override
    public void render(Camera camera, Environment environment) {
        renderer.render(camera, environment);
        waterRenderer.render(camera, environment);
        if (physicalsRenderer != null) {
            physicalsRenderer.render(camera, environment);
        }
    }

    @Override
    public void dispose() {
        renderer.dispose();
        waterRenderer.dispose();
    }
}
