package com.leibeir.lifesimulator.logic.data.tile;

import com.leibeir.lifesimulator.api.IJsonSerializable;
import com.leibeir.lifesimulator.api.IThing;

import java.util.UUID;

public class PhysicalThing implements IThing {
    private UUID uuid;
    public PhysicalThing() {
        uuid = UUID.randomUUID();
    };

    @Override
    public void fromJSON(String json) {

    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public String getUUID() {
        return uuid.toString();
    }
}
