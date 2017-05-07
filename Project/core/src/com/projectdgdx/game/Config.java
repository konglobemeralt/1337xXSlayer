package com.projectdgdx.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by Eddie on 2017-04-28.
 */
public class Config {
	static final boolean DEBUG = false;
    public static float MOVE_SPEED = 30f;

    //CAMERA VARIABLES
    public static int CAMERA_FOV = 25;
    public static final float CAMERA_NEAR = 0.01f;
    public static final float CAMERA_FAR = 500f;

    //GRAPHICS SETTINGS
    public static int AA_SAMPLES = 20;
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final boolean RESIZABLE = false;
    public static final boolean V_SYNC_ENABLED = false;
    public static final int FPS_CAP = 10000;
    public static final boolean FULLSCREEN_ENABLED = false;
    public static final String UI_SKIN_PATH = "GUIStyle/uiskin.json";

    //LIGHTS AND SHADOW MAPPING
    public static boolean SHADOWMAPPING_ENABLED = true;
    public static final int SHADOW_MAP_HEIGHT = 4048;
    public static final int SHADOW_MAP_WIDTH = 4048;
    public static final float SHADOW_MAP_VIEWPORT_HEIGHT = 100;
    public static final float SHADOW_MAP_VIEWPORT_WIDTH = 100;
    public static final float SHADOW_MAP_NEAR = 0.01f;
    public static final float SHADOW_MAP_FAR = 1500f;


    public static final float SUN_LIGHT_X = -1f;
    public static final float SUN_LIGHT_Y = -0.4f;
    public static final float SUN_LIGHT_Z = -0.2f;

    public static float SUN_LIGHT_R = 80;
    public static float SUN_LIGHT_G= 70;
    public static float SUN_LIGHT_B = 60;

    public static final float AMBIENT_LIGHT_R = 0.5f;
    public static final float AMBIENT_LIGHT_G = 0.4f;
    public static final float AMBIENT_LIGHT_B = 0.4f;
    public static final float AMBIENT_LIGHT_A = 0.4f;

    //MENUES

    public static final float MENU_DEFAULTBACKGROUND_R = 0.2f;
    public static final float MENU_DEFAULTBACKGROUND_G = 0.2f;
    public static final float MENU_DEFAULTBACKGROUND_B = 0.2f;
    public static final float MENU_DEFAULTBACKGROUND_A = 1f;;




}
