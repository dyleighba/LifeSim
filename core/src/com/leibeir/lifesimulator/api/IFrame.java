package com.leibeir.lifesimulator.api;

import com.leibeir.lifesimulator.api.tile.ItemType;

public interface IFrame extends IJsonSerializable {
    ItemType getItem(int x, int z);
    void setItem(int x, int z, ItemType item);
}
