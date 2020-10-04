package com.leibeir.lifesimulator.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leibeir.lifesimulator.LifeSimulator;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Life Simulator";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new LifeSimulator(), config);
	}
}
