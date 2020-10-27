package com.leibeir.lifesimulator.logic.data.tile;

import com.leibeir.lifesimulator.api.IJsonSerializable;
import com.leibeir.lifesimulator.api.IThing;

import java.util.UUID;

public class LivingThing implements IThing {
    private UUID uuid;

    public float hunger;
    public float hungerRate; // Increase in Hunger per tick
    public int sight; // Distance food can be seen from

    public LivingThing(){
        uuid = UUID.randomUUID();
        hunger = 50;
        hungerRate = 1.5f;
        sight = 6;
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
