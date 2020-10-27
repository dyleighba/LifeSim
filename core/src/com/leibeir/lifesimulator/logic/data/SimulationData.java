package com.leibeir.lifesimulator.logic.data;

import com.leibeir.lifesimulator.api.ISimulationData;

import java.util.ArrayList;
import java.util.List;

public class SimulationData implements ISimulationData {
    public Terrain terrain;
    public List<Epoch> epochs;

    public SimulationData(int worldSize) {
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
}
