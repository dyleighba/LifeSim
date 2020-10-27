package com.leibeir.lifesimulator.logic.data;

import com.leibeir.lifesimulator.api.IEpoch;

import java.util.ArrayList;
import java.util.List;

public class Epoch implements IEpoch {
    List<Frame> frames;

    public Epoch() {
        frames = new ArrayList<>();
    }

    @Override
    public void fromJSON(String json) {

    }

    @Override
    public String toJSON() {
        return null;
    }
}
