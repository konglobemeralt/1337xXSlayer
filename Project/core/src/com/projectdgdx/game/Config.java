package com.projectdgdx.game;

/**
 * Created by Eddie on 2017-04-28.
 *
 * Class containing various settings variables for the game.
 *
 */

public class Config {
    public static final int GAME_TIME = 12;
    public static final boolean DEBUG = false;
    public static float MOVE_SPEED = 30f;

    /************CAMERA VARIABLES************/

    //CAMERA VARIABLES
    public static int CAMERA_FOV = 55;
    public static final float CAMERA_NEAR = 1f;
    public static final float CAMERA_FAR = 500f;

    public static final float CAMERA_X = 0f;
    public static final float CAMERA_Y = 120;
    public static final float CAMERA_Z = 30f;

    /************SHADOW MAPPING VARIABLES************/

    //SHADOW MAPPING
    public static boolean SHADOWMAPPING_ENABLED = true;
    public static final int SHADOW_MAP_HEIGHT = 4048;
    public static final int SHADOW_MAP_WIDTH = 4048;
    public static final float SHADOW_MAP_VIEWPORT_HEIGHT = 200;
    public static final float SHADOW_MAP_VIEWPORT_WIDTH = 200;
    public static final float SHADOW_MAP_NEAR = 0.01f;
    public static final float SHADOW_MAP_FAR = 1500f;


    /************GRAPHICS VARIABLES************/

    //GRAPHICS SETTINGS
    public static int AA_SAMPLES = 8;
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final boolean RESIZABLE = true;
    public static final boolean V_SYNC_ENABLED = false;
    public static final int FPS_CAP = 10000;
    public static final boolean FULLSCREEN_ENABLED = false;

    /************SHADER VARIABLES************/

    //SHADER PATHS
    public static final String VERTEX_SHADER_PATH = "shaders/vertexShader.glsl";
    public static final String FRAGMENT_SHADER_PATH = "shaders/fragmentShader.glsl";


    /************LIGHTING VARIABLES************/

    //SUN LIGHT POSITION
    public static final float SUN_LIGHT_X = -1f;
    public static final float SUN_LIGHT_Y = -1f;
    public static final float SUN_LIGHT_Z = -0.6f;

    //SUN LIGHT COLOR
    public static float SUN_LIGHT_R = 8;
    public static float SUN_LIGHT_G= 7;
    public static float SUN_LIGHT_B = 6;

    //AMBIENT LIGHT COLOR
    public static final float AMBIENT_LIGHT_R = 0.2f;
    public static final float AMBIENT_LIGHT_G = 0.2f;
    public static final float AMBIENT_LIGHT_B = 0.2f;
    public static final float AMBIENT_LIGHT_A = 1f;

    //SPOT LIGHT COLOR
    public static final float SPOT_LIGHT_R = 0.9f;
    public static final float SPOT_LIGHT_G = 0.7f;
    public static final float SPOT_LIGHT_B = 0.5f;

    public static int DISCO_FACTOR = 0;

    /************MENU VARIABLES************/

    //MENU BACKGROUND COLOR
    public static final float MENU_DEFAULTBACKGROUND_R = 0.2f;
    public static final float MENU_DEFAULTBACKGROUND_G = 0.2f;
    public static final float MENU_DEFAULTBACKGROUND_B = 0.2f;
    public static final float MENU_DEFAULTBACKGROUND_A = 1f;;

    //PATHS GUI STYLE
    public static final String UI_SKIN_PATH = "GUIStyle/uiskin.json";

    /************MODEL VARIABLES************/

    //MODEL RADIUS'S
    public static final float HONEST_ACT_DISTANCE = 20;
    public static final float USE_ABILITY_ACT_DISTANCE = 5;
    public static final float STRIKE_ZONE_RADIUS = 10;
    public static final float WORKER_NODE_RADIUS = 1;
    public static final float BLACKOUT_RADIUS = 15;

    //MODEL ATTRIBUTES
    public static final float MAX_SPEED = 30f;

    /************LEVEL SELECTION************/
    public static String LEVEL_IN_PLAY = "basicMap.txt";
}