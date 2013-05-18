package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.thoughtworks.xstream.XStream;

import ui.Timer;



public class Config {
	
	public static final String TITLE = "Seasons";
	
	// Resolution
	public static final int RESOLUTION_WIDTH = 1366;
	public static final int RESOLUTION_HEIGHT = 768;
	public static final boolean FULLSCREEN = false;
	
	// Frame Rate
	public static final int ACTIVE_FRAME_RATE = 60;
	public static final int INACTIVE_FRAME_RATE = 10;
	
	// Physics
	public static final float GRAVITY = 50;
	public static final float DEFAULT_DENSITY = 1f;
	public static final float DEFAULT_FRICTION = .03f;
	public static final float DEFAULT_TRACTION = 20f; // Friction for feet
	public static final float PIXELS_PER_METER = 32;
	public static final float VEL_EPSILON = .5f;
	public static final int VELOCITY_ITERATIONS = 6;
	public static final int POSITION_ITERATIONS = 2;
	
	// Map parsing
	public static final int[] WALL_IDS = {2};
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	public static final String MAP_PATH = "assets/maps/";
	public static final String BACKGROUND_PATH = "assets/backgrounds/";
	
	// Object Filter Categories
	public static final int HOOKABLE = 2;
	public static final int WATER = 4;
	public static final int SALMON = 8;
	public static final int MUSHROOM = 16;
	public static final int STATICENTITY = 24;
	
	// Player
	public static final float PLAYER_DENSITY = 1f;
	public static final int PLAYER_WIDTH = 36;
	public static final int PLAYER_HEIGHT = 80;
	public static final int PLAYER_DRAW_WIDTH = 80;
	public static final int PLAYER_DRAW_HEIGHT = 80;
	public static final int PLAYER_GROUND = 6;
	public static final int PLAYER_MAX_HP = 1;
	public static final float PLAYER_MOVE_SPEED = 35f;
	public static final float PLAYER_ACCELERATION = 100;
	public static final float PLAYER_MAX_AIR_SPEED = 19;
	public static final float PLAYER_AIR_ACCELERATION = 1f;
	public static final float PLAYER_JUMP_SPEED = 115;
	public static final float PLAYER_WATER_MOVE_SPEED = 2f;

	// Hookshot
	public static final float HOOKSHOT_SHOOT_VEL = 100;
	public static final float HOOKSHOT_PULL_VEL = 40;
	public static final float HOOKSHOT_MAX_RANGE = 500;
	public static final float HOOKSHOT_TOLERANCE = 30;
	
	// Movement
	public static final float WATER_GRAVITY_SCALE = -1f;
	public static final float WATER_DRAG = 10;
	
	// UI
	// how far away from the player the aiming cursor appears
	public static final float CURSOR_DIST = 100;
	public static final int CURSOR_SIZE = 15;
	public static final int SALMON_TIME = -1000;
	
	public static HashMap<Integer, Timer> times;
	
	@SuppressWarnings("unchecked")
	public static void loadTimes() {
		XStream x = new XStream();
		try {
			File f = new File("times.xml");
			if (f.exists()) {
				times = (HashMap<Integer, Timer>) x.fromXML(new FileInputStream(f));
			} else {
				times = new HashMap<Integer, Timer>();
				for (Section s : Section.values()) {
					times.put(s.getID(), new Timer());
				}
				saveTimes();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveTimes() {
		XStream x = new XStream();
		File f = new File("times.xml");
		try {
			if (f.exists()) f.delete();
			f.createNewFile();
			FileWriter w = new FileWriter(f);
			x.toXML(times, w);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
