package com.projectdgdx.game;

/**
 * Created by Eddie on 2017-04-28.
 */
public class Config {
	static final boolean DEBUG = false;
    public static final float MOVE_SPEED = 0.05f;

    //CAMERA VARIABLES
    public static final int CAMERA_FOV = 25;
    public static final float CAMERA_NEAR = 0.01f;
    public static final float CAMERA_FAR = 500f;

    //GRAPHICS SETTINGS
    public static final int AA_SAMPLES = 20;
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final boolean RESIZABLE = false;
    public static final boolean V_SYNC_ENABLED = false;
    public static final int FPS_CAP = 10000;

    //LIGHTS AND SHADOW MAPPING
    public static boolean SHADOWMAPPING_ENABLED = false;
    public static final int SHADOW_MAP_HEIGHT = 4048;
    public static final int SHADOW_MAP_WIDTH = 4048;
    public static final float SHADOW_MAP_VIEWPORT_HEIGHT = 100;
    public static final float SHADOW_MAP_VIEWPORT_WIDTH = 100;
    public static final float SHADOW_MAP_NEAR = 0.01f;
    public static final float SHADOW_MAP_FAR = 1500f;


    public static final float SUN_LIGHT_X = -1f;
    public static final float SUN_LIGHT_Y = -0.4f;
    public static final float SUN_LIGHT_Z = -0.2f;

    public static final float SUN_LIGHT_R = 0.8f;
    public static final float SUN_LIGHT_G= 0.7f;
    public static final float SUN_LIGHT_B = 0.6f;

    public static final float AMBIENT_LIGHT_R = 0.5f;
    public static final float AMBIENT_LIGHT_G = 0.4f;
    public static final float AMBIENT_LIGHT_B = 0.4f;
    public static final float AMBIENT_LIGHT_A = 0.4f;




}
