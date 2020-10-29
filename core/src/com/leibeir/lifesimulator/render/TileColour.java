package com.leibeir.lifesimulator.render;

import com.badlogic.gdx.graphics.Color;
import com.leibeir.lifesimulator.api.tile.TileType;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.HashMap;
import java.util.Map;

public class TileColour {
    // This is done this way to avoid incorrect colours if the TileType enum is changed
    private static Map<TileType, Color> colourMapping;
    static {
        colourMapping = new HashMap<>();
        colourMapping.put(TileType.OutOfBounds, Color.RED);
        colourMapping.put(TileType.Water, new Color(0x82A7A6BB));
        colourMapping.put(TileType.DeepWater, new Color(0x82A7A6BB));
        colourMapping.put(TileType.Gravel, new Color(0x80808000));
        colourMapping.put(TileType.Dirt, new Color(0x65432100));
        colourMapping.put(TileType.Grass, new Color(0x5a691400));
    }

    public static Color get(TileType type) {
        Color colour = colourMapping.get(type);
        if (colour == null) throw new ValueException(String.format("%s doesn't have a defined colour.", type.name()));
        return colour;
    }
}
