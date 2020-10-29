package com.leibeir.lifesimulator.logic.data;

import com.leibeir.lifesimulator.api.ITerrain;
import com.leibeir.lifesimulator.api.tile.PhysicalType;
import com.leibeir.lifesimulator.api.tile.TileType;
import com.leibeir.lifesimulator.logic.terrain.TerrainGeneration;
import com.leibeir.lifesimulator.util.MathHelper;

import java.util.Random;

public class Terrain implements ITerrain {
    private final int size;
    protected PhysicalType[][] physicalTypes;
    private float[][] elevationMap;
    private TileType[][] typeMap;
    private float seaLevel;
    public final short seed;

    public Terrain(int size) {
        this.size = size;
        Random rand = new Random();
        this.seed = (short)rand.nextInt(Short.MAX_VALUE);
        setup();
    }

    public Terrain(int size, short seed) {
        this.size = size;
        this.seed = seed;
        setup();
    };

    private void setup() {
        elevationMap = TerrainGeneration.genElevationMap(size, seed);
        typeMap = TerrainGeneration.genTypeMap(elevationMap);
        physicalTypes = TerrainGeneration.genPhysicals(elevationMap, typeMap, seed);
        this.seaLevel = TerrainGeneration.SEA_LEVEL;
    }

    @Override
    public void fromJSON(String json) {

    }

    @Override
    public String toJSON() {
        return null;
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
        return getType(x, z) != TileType.OutOfBounds && getPhysicalType(x, z) != PhysicalType.Nothing;
    }

    @Override
    public TileType getType(int x, int z) {
        if ((x < 0 || x >= size) || (z < 0 || z >= size)) {
            return TileType.OutOfBounds;
        }
        return typeMap[x][z];
    }

    @Override
    public PhysicalType getPhysicalType(int x, int z) {
        if ((x < 0 || x >= size) || (z < 0 || z >= size)) {
            return PhysicalType.Nothing;
        }
        PhysicalType pType = physicalTypes[x][z];
        if (pType == null) pType = PhysicalType.Nothing;
        return pType;
    }
}
