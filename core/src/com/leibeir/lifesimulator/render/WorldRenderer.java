package com.leibeir.lifesimulator.render;

import com.badlogic.gdx.graphics.Color;
import com.leibeir.lifesimulator.world.World;

public abstract class WorldRenderer extends Renderer {
    public final World world;

    public final static Color grass = new Color(0x5a691400);
    public final static Color sand = new Color(0x80808000);
    public final static Color deepSand = new Color(0x65432100);

    public WorldRenderer(World world) {
        this.world = world;
    }
}
