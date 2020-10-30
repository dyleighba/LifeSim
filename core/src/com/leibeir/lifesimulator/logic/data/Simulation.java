package com.leibeir.lifesimulator.logic.data;

import com.leibeir.lifesimulator.api.ISimulationData;
import com.leibeir.lifesimulator.api.tile.ItemType;
import com.leibeir.lifesimulator.api.tile.PhysicalType;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements ISimulationData {
    public Terrain terrain;
    public List<Epoch> epochs;

    public Simulation(int worldSize) {
        terrain = new Terrain(worldSize);
        epochs = new ArrayList<>();
    }

    @Override
    public void fromJSON(String json) {

    }

    @Override
    public String toJSON() {
        return null;
    }

    // TODO add functions like nextFrame etc
    public Frame getCurrentFrame() {
        // For testing ItemRenderer
        Frame f = new Frame(terrain.getSize());
        for (int i=0; i<10; i++) {
            if (terrain.getPhysicalType(0, i) == PhysicalType.Nothing)
                f.setItem(0, i, ItemType.Snack);
        }
        for (int i=0; i<16; i++) {
            if (terrain.getPhysicalType(2, i) == PhysicalType.Nothing)
                f.setItem(2, i, ItemType.Meal);
        }
        return f;
    }
}
