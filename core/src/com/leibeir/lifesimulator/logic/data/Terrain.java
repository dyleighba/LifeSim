package com.leibeir.lifesimulator.logic.data;

import com.leibeir.lifesimulator.api.ITerrain;
import com.leibeir.lifesimulator.logic.data.tile.PhysicalThing;

public class Terrain implements ITerrain {
    private PhysicalThing[][] physicalThings;
    private float seaLevel;

    public Terrain(int size) {
        physicalThings = new PhysicalThing[size][size];
    };

    @Override
    public void fromJSON(String json) {

    }

    @Override
    public String toJSON() {
        return null;
    }
}
