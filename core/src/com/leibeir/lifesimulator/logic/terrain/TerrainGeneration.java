package com.leibeir.lifesimulator.logic.terrain;

import com.leibeir.lifesimulator.api.tile.PhysicalType;
import com.leibeir.lifesimulator.api.tile.TileType;
import com.leibeir.lifesimulator.external.OpenSimplexNoise;

import java.util.Random;

public class TerrainGeneration {
    public static float SEA_LEVEL = 0f;
    public static float TERRAIN_SMOOTHNESS = 0.01f;
    public static float TERRAIN_SMOOTHNESS_B = 0.08f;
    public static float TERRAIN_INTENSITY = 5f;
    public static float TERRAIN_INTENSITY_B = 1f;
    public static float LAND_SHIFT = 1f;
    public static float SHORE_THRESHOLD = 0.9f;

    public static TileType SHORE_TYPE = TileType.Gravel;
    public static TileType LAND_TYPE = TileType.Grass;
    public static TileType DEEPWATER_TYPE = TileType.Dirt;

    private static final OpenSimplexNoise noise = new OpenSimplexNoise();

    public static float[][] genElevationMap(int size, int seed) {
        float[][] elevationMap = new float[size][size];
        for (int x=0; x<size; x++){
            for (int z=0; z<size; z++){
                float p1 = (TERRAIN_INTENSITY * (float)noise.eval(TERRAIN_SMOOTHNESS*(x+seed), TERRAIN_SMOOTHNESS*(z+seed))) + LAND_SHIFT;
                float p2 = (TERRAIN_INTENSITY_B * (float)noise.eval(TERRAIN_SMOOTHNESS_B*(x+seed+535), TERRAIN_SMOOTHNESS_B*(z+seed+535))) + LAND_SHIFT;
                elevationMap[x][z] = p1 + p2;
            }
        }
        return elevationMap;
    }

    public static TileType[][] genTypeMap(float[][] elevationMap) {
        int size = elevationMap.length;
        TileType[][] typeMap = new TileType[size][size];
        for (int x=0; x<size; x++){
            for (int z=0; z<size; z++){
                TileType tileType = LAND_TYPE;
                if (elevationMap[x][z]+(SHORE_THRESHOLD/2) <= SEA_LEVEL) {
                    tileType = TileType.DeepWater;
                }
                else if (elevationMap[x][z] <= SEA_LEVEL) {
                    tileType = TileType.Water;
                }
                else if (elevationMap[x][z]-SHORE_THRESHOLD <= SEA_LEVEL) {
                    tileType = SHORE_TYPE;
                }
                typeMap[x][z] = tileType;
            }
        }
        return typeMap;
    }

    public static PhysicalType[][] genPhysicals(float[][] elevationMap, TileType[][] typeMap, short worldSeed) {
        assert elevationMap.length == typeMap.length;
        int size = elevationMap.length;
        PhysicalType[][] physicals = new PhysicalType[size][size];
        Random rand = new Random(worldSeed);
        for (int x=0; x<size; x++){
            for (int z=0; z<size; z++){
                if (typeMap[x][z] == TileType.Grass){
                    int selection = rand.nextInt(PhysicalType.values().length*10);
                    if (selection >= PhysicalType.values().length-1) {
                        physicals[x][z] = PhysicalType.Nothing;
                    }
                    else {
                        physicals[x][z] = PhysicalType.values()[selection+1];
                    }
                }
            }
        }
        return physicals;
    }
}
