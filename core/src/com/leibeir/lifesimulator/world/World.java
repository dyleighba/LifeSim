package com.leibeir.lifesimulator.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.leibeir.lifesimulator.api.IWorld;
import com.leibeir.lifesimulator.external.OpenSimplexNoise;
import com.leibeir.lifesimulator.util.MathHelper;

import java.util.Random;

public class World implements IWorld {
    private final int size;
    private float[][] elevationMap;
    private final OpenSimplexNoise noise = new OpenSimplexNoise();
    public static float terrainSmoothness = 0.01f;
    public static float terrainSmoothnessB = 0.08f;
    public static float terrainIntensity = 5f;
    public static float terrainIntensityB = 1f;
    public static float landShift = 1f;
    public static float seaLevel = 0f;
    public static float sandThreshold = 0.9f;
    public final short seed;
    private final TerrainMeshRenderer terrainRenderer;
    private final WaterRenderer waterRenderer;

    public World(int size){
        Random rand = new Random();
        seed = (short)rand.nextInt(Short.MAX_VALUE);
        this.size = size;
        generateWorld();
        terrainRenderer = new TerrainMeshRenderer(this);
        waterRenderer = new WaterRenderer(this);
    }

    private void generateWorld(){
        elevationMap = new float[size][size];
        for (int x=0; x<size; x++){
            for (int z=0; z<size; z++){
                float p1 = (terrainIntensity * (float)noise.eval(terrainSmoothness*(x+seed), terrainSmoothness*(z+seed))) + landShift;
                float p2 = (terrainIntensityB * (float)noise.eval(terrainSmoothnessB*(x+seed+535), terrainSmoothnessB*(z+seed+535))) + landShift;
                elevationMap[x][z] = p1 + p2;
            }
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public float getSeaLevel() {
        return seaLevel;
    }

    @Override
    public float getElevation(int x, int z) {
        x = MathHelper.minmax(0, size-1, x);
        z = MathHelper.minmax(0, size-1, z);
        return elevationMap[x][z];
    }

    @Override
    public boolean isPassable(int x, int z) {
        return !isDeepWater(x, z);
    }

    @Override
    public boolean isSand(int x, int z) {
        return !isWater(x, z) && getElevation(x, z)-sandThreshold <= seaLevel;
    }

    @Override
    public boolean isWater(int x, int z) {
        return getElevation(x, z) <= seaLevel;
    }

    @Override
    public boolean isDeepWater(int x, int z) {
        return getElevation(x, z)+(sandThreshold/2) <= seaLevel;
    }

    @Override
    public boolean isGrass(int x, int z) {
        // TODO test
        return getElevation(x, z)-sandThreshold > seaLevel;
    }

    @Override
    public boolean isStone(int x, int z) {
        return false;
    }

    @Override
    public void render(Camera camera, Environment environment) {
        terrainRenderer.render(camera, environment);
        waterRenderer.render(camera, environment);
    }

    @Override
    public void dispose() {
        terrainRenderer.dispose();
        waterRenderer.dispose();
    }
}
