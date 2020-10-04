package com.leibeir.lifesimulator.api;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;

public interface IRenderer {
    void update();
    void render(Camera camera, Environment environment);
    void dispose();
}
