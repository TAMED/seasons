package config;


public class Config {
	
	public static final String TITLE = "Seasons";
	
	// Resolution
	public static final int RESOLUTION_WIDTH = 1366;
	public static final int RESOLUTION_HEIGHT = 768;
	public static final boolean FULLSCREEN = false;
	
	// Physics
	public static final float PIXELS_PER_METER = 32;
	public static final float DEFAULT_DENSITY = 1f;
	public static final float DEFAULT_FRICTION = 10f;
	public static final float DEFAULT_TRACTION = 100f; // Friction for feet
	public static final int VELOCITY_ITERATIONS = 6;
	public static final int POSITION_ITERATIONS = 2;
	public static final float VEL_EPSILON = .2f;
	
	// Map parsing
	public static final int[] WALL_IDS = {2};
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	
	// Object Filter Categories
	public static final int HOOKABLE = 2;
	public static final int WATER = 4;
	
	// Player
	public static final float PLAYER_DENSITY = 1f;
	public static final int PLAYER_WIDTH = 36;
	public static final int PLAYER_HEIGHT = 80;
	public static final int PLAYER_DRAW_WIDTH = 80;
	public static final int PLAYER_DRAW_HEIGHT = 80;
	public static final int PLAYER_GROUND = 6;
	public static final int PLAYER_MAX_HP = 1;
	public static final float PLAYER_MOVE_SPEED = 50;
	public static final float PLAYER_AIR_MOVE_ACC = 5f;
	public static final float PLAYER_WATER_MOVE_SPEED = 0.5f;
	public static final float PLAYER_ACCELERATION = 100;
	public static final float PLAYER_JUMP_SPEED = 80;

	
	// Movement
	public static final float GRAVITY = 25;
	public static final float DRAG = 0.5f;
	public static final float WATER_GRAVITY_SCALE = -1f;
	
	// UI
	// how far away from the player the aiming cursor appears
	public static final float CURSOR_DIST = 100;
	public static final int CURSOR_SIZE = 15;
}
