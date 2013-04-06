package config;

import camera.Camera;

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
	
	// Player
	public static final float PLAYER_MOVE_SPEED = 8;
	public static final float PLAYER_JUMP_SPEED = 8;
	public static final int PLAYER_MAX_HP = 1;
	
	// Movement
	public static final float GRAVITY = 10;
	public static final float DRAG = 0.5f;
	
	// UI
	// how far away from the player the aiming cursor appears
	public static final float CURSOR_DIST = 100;
	public static final int CURSOR_SIZE = 10;
	
	// THIS IS A GIANT HACK. Please remove once there's a framework for appstates
	public static Camera camera;
}
