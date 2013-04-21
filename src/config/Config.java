package config;


public class Config {
	
	public static final String TITLE = "Seasons";
	
	// Physics
	public static final float PIXELS_PER_METER = 32;
	public static final float DEFAULT_DENSITY = 1f;
	public static final float DEFAULT_FRICTION = 0.0001f;
	public static final int VELOCITY_ITERATIONS = 6;
	public static final int POSITION_ITERATIONS = 2;
	
	// Map parsing
	public static final int[] WALL_IDS = {2};
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	
	// Object Groups
	public static final int HOOKABLE = 2;
	
	// Player
	public static final float PLAYER_MOVE_SPEED = 25;
	public static final float PLAYER_JUMP_SPEED = 20;
	public static final int PLAYER_MAX_HP = 1;
	
	// Movement
	public static final float GRAVITY = 10;
	public static final float DRAG = 0.5f;
	
	// UI
	// how far away from the player the aiming cursor appears
	public static final float CURSOR_DIST = 100;
	public static final int CURSOR_SIZE = 15;
}
