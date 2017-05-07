package com.projectdgdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.ProjectD;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Project D";
		config.width = Config.SCREEN_WIDTH;
		config.height =  Config.SCREEN_HEIGHT;
		config.resizable = Config.RESIZABLE;
		config.vSyncEnabled = Config.V_SYNC_ENABLED;
		config.foregroundFPS = Config.FPS_CAP;
		config.fullscreen = Config.FULLSCREEN_ENABLED;
		config.samples = Config.AA_SAMPLES;
		new LwjglApplication(new ProjectD(), config);

	}
}
