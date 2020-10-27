package com.leibeir.lifesimulator.logic.data.tile;

import com.leibeir.lifesimulator.api.IJsonSerializable;
import com.leibeir.lifesimulator.api.IThing;

import java.util.UUID;

public class ItemThing implements IThing {
    private UUID uuid;

    public ItemThing() {
        uuid = UUID.randomUUID();
    }

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
