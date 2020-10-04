package com.leibeir.lifesimulator.api;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;

public interface IWorld {
    int getSize();
    float getSeaLevel();
    float getElevation(int x, int z);
    boolean isPassable(int x, int z);
    boolean isSand(int x, int z);
    boolean isWater(int x, int z);
    boolean isDeepWater(int x, int z);
    boolean isGrass(int x, int z);
    boolean isStone(int x, int z);
    void render(Camera camera, Environment environment);
    void dispose();
    //TODO use some kind of different interface for render and dispose, possibly from libgdx
}
