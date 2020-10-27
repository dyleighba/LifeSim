package com.leibeir.lifesimulator.logic.data;

import com.leibeir.lifesimulator.api.IFrame;

import java.util.ArrayList;
import java.util.List;

public class Frame implements IFrame {
    public List<Tile> tiles;

    public Frame() {
        tiles = new ArrayList<>();
    }

    @Override
    public void fromJSON(String json) {

    }

    @Override
    public String toJSON() {
        return null;
    }
}
