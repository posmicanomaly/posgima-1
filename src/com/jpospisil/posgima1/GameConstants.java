package com.jpospisil.posgima1;

import org.jsfml.graphics.Color;
import org.jsfml.window.VideoMode;

public final class GameConstants
{
	public static final boolean	DEBUG_WINDOWED			= true;
	public static boolean		won						= false;

	public static boolean		PLAYER_INTENT_TO_MINE	= false;
	public static int			MINIMUM_HEALTH			= 1;
	public static int			MAXIMUM_HUNGER			= 10;
	public static boolean		RENDER_REQUIRED			= false;
	public static int			WIDTH					= VideoMode
																.getDesktopMode().width;
	public static int			HEIGHT					= VideoMode
																.getDesktopMode().height;

	public static float			VIEWPORT_TOP_X			= .15f;
	public static float			VIEWPORT_TOP_Y			= 0f;
	public static float			VIEWPORT_BOTTOM_X		= .85f;
	public static float			VIEWPORT_BOTTOM_Y		= .70f;

	// public static int WIDTH = SettingsImporter.WIDTH;
	// public static int HEIGHT = SettingsImporter.HEIGHT;
	public static boolean		KEY_REPEAT_STATUS		= false;

	public static boolean		DEBUG					= true;
	public static String		GAME_NAME				= "PosgimA";
	public static String		VERSION					= "0.4a";
	public static String		BUILD_DATE				= "2/2/2014";
	public static String		BUILD_TIME				= "1:05 AM";
	public static String		FORMATTED_GAME_INFO		= GAME_NAME
																+ " - build: "
																+ VERSION
																+ " ("
																+ BUILD_DATE
																+ " @ "
																+ BUILD_TIME
																+ ")";

	public static int			DEFAULT_FONT_SIZE		= HEIGHT / 40;
	// public static int DEFAULT_FONT_SIZE = VideoMode.getDesktopMode().height /
	// 40;
	public static int			SIDE_UI_FONT_SIZE		= (int) (DEFAULT_FONT_SIZE / 1.5f);
	public static int			BOTTOM_UI_FONT_SIZE		= (int) (DEFAULT_FONT_SIZE / 1.5f);
	public static SFMLFont		FONTS					= new SFMLFont();

	public static int			MAP_GENERATOR_COLS		= 40;
	public static int			MAP_GENERATOR_ROWS		= 40;

	public static int			MAX_FOOD_SEED			= (MAP_GENERATOR_COLS * MAP_GENERATOR_ROWS) / 16;
	public static int			MAX_DEER_SEED			= MAX_FOOD_SEED;
	public static int			MAP_FEATURE_SEED_MAX	= (MAP_GENERATOR_COLS * MAP_GENERATOR_ROWS) / 20;
	public static int			MAP_MAX_SEED_STRENGTH	= 100;
	public static String		DEATH_MESSAGE			= null;

	public static float			DEFAULT_ZOOM_LEVEL		= 1;

	// set to be higher than they should so i can zoom out right now
	public static int			VISIBLE_LEFT			= (int) ((20 * 2) * DEFAULT_ZOOM_LEVEL);

	public static int			VISIBLE_RIGHT			= (int) ((19 * 2) * DEFAULT_ZOOM_LEVEL);
	public static int			VISIBLE_UP				= (int) ((12 * 2) * DEFAULT_ZOOM_LEVEL);
	public static int			VISIBLE_DOWN			= (int) ((10 * 2) * DEFAULT_ZOOM_LEVEL);
	// public static int REMOVE_PADDING_BETWEEN_TILES = 4;
	public static double		MAP_LAVA_FREQ_MOD		= 0.25f;

	public static void printDebugMessage(String error)
	{
		if (DEBUG)
		{
			System.out.println("DEBUG: " + error);
		}
	}

	public static void resetSeedValues()
	{

		if (MAP_GENERATOR_COLS > 500)
		{
			MAP_GENERATOR_COLS = 500;
			MAP_GENERATOR_ROWS = 500;
		}
		else if (MAP_GENERATOR_COLS < 20)
		{
			MAP_GENERATOR_COLS = 20;
			MAP_GENERATOR_ROWS = 20;
		}
		MAP_FEATURE_SEED_MAX = (MAP_GENERATOR_COLS * MAP_GENERATOR_ROWS) / 20;
		MAX_FOOD_SEED = (MAP_GENERATOR_COLS * MAP_GENERATOR_ROWS) / 8;
	}

	public static void toggleKeyRepeat(SFMLRenderWindow window)
	{
		if (KEY_REPEAT_STATUS)
			KEY_REPEAT_STATUS = false;
		else
			KEY_REPEAT_STATUS = true;
		window.getRenderWindow().setKeyRepeatEnabled(KEY_REPEAT_STATUS);
	}
}
