package com.leibeir.lifesimulator.logic.data;

import com.leibeir.lifesimulator.api.ITerrain;
import com.leibeir.lifesimulator.api.TerrainType;
import com.leibeir.lifesimulator.logic.data.tile.PhysicalThing;
import com.leibeir.lifesimulator.logic.terrain.TerrainGeneration;
import com.leibeir.lifesimulator.util.MathHelper;

import java.util.Random;

public class Terrain implements ITerrain {
    private final int size;
    private PhysicalThing[][] physicalThings;
    private float[][] elevationMap;
    private TerrainType[][] typeMap;
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
        physicalThings = new PhysicalThing[this.size][this.size];
        elevationMap = TerrainGeneration.genElevationMap(this.size, this.seed);
        typeMap = TerrainGeneration.genTypeMap(elevationMap);
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
        return getType(x, z) != TerrainType.OutOfBounds && physicalThings[x][z] == null;
    }

    @Override
    public TerrainType getType(int x, int z) {
        if ((x < 0 || x >= size) || (z < 0 || z >= size)) {
            return TerrainType.OutOfBounds;
        }
        return typeMap[x][z];
    }

    @Override
    public PhysicalThing getPhysicalThing(int x, int z) {
        if ((x < 0 || x >= size) || (z < 0 || z >= size)) {
            return null;
        }
        return physicalThings[x][z];
    }
}
