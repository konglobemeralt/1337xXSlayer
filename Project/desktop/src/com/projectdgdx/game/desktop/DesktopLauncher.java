package com.projectdgdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.projectdgdx.game.controller.ProjectD;
import com.projectdgdx.game.utils.Vector2d;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Project D";
		config.width = Vector2d.Config.SCREEN_WIDTH;
		config.height =  Vector2d.Config.SCREEN_HEIGHT;
		config.resizable = Vector2d.Config.RESIZABLE;
		config.vSyncEnabled = Vector2d.Config.V_SYNC_ENABLED;
		config.foregroundFPS = Vector2d.Config.FPS_CAP;
		config.fullscreen = Vector2d.Config.FULLSCREEN_ENABLED;
		config.samples = Vector2d.Config.AA_SAMPLES;
		new LwjglApplication(new ProjectD(), config);
	}
}
