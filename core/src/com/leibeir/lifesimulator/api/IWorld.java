package com.leibeir.lifesimulator.api;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;

public interface IWorld {
    int getSize();
    void render(Camera camera, Environment environment);
    void dispose();
    //TODO use some kind of different interface for render and dispose, possibly from libgdx
}
