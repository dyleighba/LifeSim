package com.leibeir.lifesimulator.logic.terrain;

import com.leibeir.lifesimulator.api.TerrainType;
import com.leibeir.lifesimulator.external.OpenSimplexNoise;

public class TerrainGeneration {
    public static float SEA_LEVEL = 0f;
    public static float TERRAIN_SMOOTHNESS = 0.01f;
    public static float TERRAIN_SMOOTHNESS_B = 0.08f;
    public static float TERRAIN_INTENSITY = 5f;
    public static float TERRAIN_INTENSITY_B = 1f;
    public static float LAND_SHIFT = 1f;
    public static float SHORE_THRESHOLD = 0.9f;

    public static TerrainType SHORE_TYPE = TerrainType.Gravel;
    public static TerrainType LAND_TYPE = TerrainType.Grass;
    public static TerrainType DEEPWATER_TYPE = TerrainType.Dirt;

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

    public static TerrainType[][] genTypeMap(float[][] elevationMap) {
        int size = elevationMap.length;
        TerrainType[][] typeMap = new TerrainType[size][size];
        for (int x=0; x<size; x++){
            for (int z=0; z<size; z++){
                TerrainType terrainType = TerrainType.Grass;
                if (elevationMap[x][z]+(SHORE_THRESHOLD/2) <= SEA_LEVEL) {
                    terrainType = TerrainType.DeepWater;
                }
                else if (elevationMap[x][z] <= SEA_LEVEL) {
                    terrainType = TerrainType.Water;
                }
                else if (elevationMap[x][z]-SHORE_THRESHOLD <= SEA_LEVEL) {
                    terrainType = SHORE_TYPE;
                }
                typeMap[x][z] = terrainType;
            }
        }
        return typeMap;
    }
}
