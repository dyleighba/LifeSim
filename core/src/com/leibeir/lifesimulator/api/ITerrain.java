package com.leibeir.lifesimulator.api;

import com.leibeir.lifesimulator.logic.data.Terrain;
import com.leibeir.lifesimulator.logic.data.tile.PhysicalThing;

public interface ITerrain extends IJsonSerializable {
    int getSize();
    float getSeaLevel();
    float getElevation(int x, int z);
    boolean isPassable(int x, int z);
    TerrainType getType(int x, int z);
    PhysicalThing getPhysicalThing(int x, int z);
}
