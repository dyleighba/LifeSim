package com.leibeir.lifesimulator.api;

import com.leibeir.lifesimulator.logic.data.tile.ItemThing;
import com.leibeir.lifesimulator.logic.data.tile.LivingThing;

public interface ITile extends IJsonSerializable {
    ItemThing item = null;
    LivingThing living = null;
}
