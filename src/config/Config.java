package config;

public class Config {
	
	public static final String TITLE = "Seasons";
	
	// Physics
	public static final float PIXELS_PER_METER = 32;
	public static final float DEFAULT_DENSITY = 1f;
	public static final float DEFAULT_FRICTION = 0.0001f;
	
	// Map parsing
	public static final int[] WALL_IDS = {2};
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	
	// Movement
	public static final float JUMP_VEL = 8f;
	public static final float MOVE_VEL = 8f;
	public static final int JUMP_TIMER = 15;
	public static final int TOP = 0;
	public static final int BOTTOM = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
}
