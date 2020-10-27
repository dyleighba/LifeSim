package com.leibeir.lifesimulator.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.leibeir.lifesimulator.api.IWorld;
import com.leibeir.lifesimulator.api.TerrainType;
import com.leibeir.lifesimulator.external.OpenSimplexNoise;
import com.leibeir.lifesimulator.logic.data.Terrain;
import com.leibeir.lifesimulator.util.MathHelper;

import java.util.Random;

public class World implements IWorld {
    private final int size;
    private final WorldRenderer renderer;
    private final WaterRenderer waterRenderer;
    private Terrain terrain;

    public World(int size){
        this.size = size;
        terrain = new Terrain(this.size);
        renderer = new TerrainMeshRenderer(this);
        waterRenderer = new WaterRenderer(this);
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
        return terrain.getType(x, z) == TerrainType.Sand;
    }

    @Override
    public boolean isWater(int x, int z) {
        return terrain.getType(x, z) == TerrainType.Water;
    }

    @Override
    public boolean isDeepWater(int x, int z) {
        return terrain.getType(x, z) == TerrainType.DeepWater;
    }

    @Override
    public boolean isGrass(int x, int z) {
        return terrain.getType(x, z) == TerrainType.Grass;
    }

    @Override
    public boolean isStone(int x, int z) {
        return terrain.getType(x, z) == TerrainType.Stone;
    }

    @Override
    public void render(Camera camera, Environment environment) {
        renderer.render(camera, environment);
        waterRenderer.render(camera, environment);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        waterRenderer.dispose();
    }
}
