package com.projectdgdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.ProjectD;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.resizable = false;
		config.vSyncEnabled = false;
		config.samples = 20;
		config.foregroundFPS = 10000;
		new LwjglApplication(new ProjectD(), config);

	}
}
