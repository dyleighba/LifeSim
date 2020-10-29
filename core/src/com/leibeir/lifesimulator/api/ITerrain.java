package com.leibeir.lifesimulator.api;

import com.leibeir.lifesimulator.api.tile.PhysicalType;
import com.leibeir.lifesimulator.api.tile.TileType;

public interface ITerrain extends IJsonSerializable {
    int getSize();
    float getSeaLevel();
    float getElevation(int x, int z);
    boolean isPassable(int x, int z);
    TileType getType(int x, int z);
    PhysicalType getPhysicalType(int x, int z);
}
