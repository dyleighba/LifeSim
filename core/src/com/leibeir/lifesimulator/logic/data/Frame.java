package com.leibeir.lifesimulator.logic.data;

import com.leibeir.lifesimulator.api.IFrame;
import com.leibeir.lifesimulator.api.tile.ItemType;
import com.leibeir.lifesimulator.logic.data.tile.LivingThing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Frame implements IFrame {
    private final ItemType[][] items;
    private final LivingThing[][] livings;

    public Frame(int size) {
        items = new ItemType[size][size];
        for (ItemType[] row: items)
            Arrays.fill(row, ItemType.Nothing);
        livings = new LivingThing[size][size];
    }

    @Override
    public void fromJSON(String json) {

    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public ItemType getItem(int x, int z) {
        return items[x][z];
    }

    @Override
    public void setItem(int x, int z, ItemType item) {
        items[x][z] = item;
    }
}
