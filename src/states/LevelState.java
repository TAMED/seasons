package states;

import input.Controls;

import java.util.ArrayList;
import java.util.Iterator;

import main.MainGame;
import map.Map;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.Cursor;
import ui.Time;
import ui.Timer;
import util.Box2DDebugDraw;
import camera.Camera;

import combat.CombatContact;

import config.Config;

import entities.Player;
import entities.enemies.Enemy;

public class LevelState extends BasicGameState{
	private int id;
	private String mapString;
	private Map map;
	private Vec2 gravity;
	private World world;
	private Player player;
	private ArrayList<Enemy> enemies;
	private Box2DDebugDraw debugdraw;
	private boolean viewDebug = false;
	public static boolean godMode = false;
	private static Camera camera;
	private Cursor cursor;
	private Vec2 goalLoc;
	private String backgroundString;
	private Image background;
	private Timer timer;
	private Time lastTime;
	private Time bestTime;
	
	public LevelState(String mapString, String backgroundString, int id) {
		this(mapString, backgroundString, id, new Vec2(0, Config.GRAVITY));
	}
	
	public LevelState(String mapString, String backgroundString, int id, Vec2 gravity) {
		super();
		this.id = id;
		this.mapString = mapString;
		this.backgroundString = backgroundString;
		this.gravity = gravity;
		
	}
		
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics)
			throws SlickException {
		camera.translateGraphics();
		drawBackground(graphics);
		camera.untranslateGraphics();
		camera.drawMap();
		camera.translateGraphics();
		
		
		if (viewDebug) {
			debugdraw.setGraphics(graphics);
			world.drawDebugData();
		} else {
			player.render(graphics);
			for (Enemy e : enemies) {
				e.render(graphics);			
			}
		}
		cursor.render(graphics);
		
		// timer draw
		// HACK: remove once there's a parallax system
		graphics.setColor(Color.white);
		graphics.drawString(timer.getTimeString(), camera.getPosition().getMinX()+100, camera.getPosition().getMinY()+100);

		graphics.drawString("Last " + lastTime.getTimeString(), camera.getPosition().getMinX()+100, camera.getPosition().getMinY()+125);
		graphics.drawString("Best " + bestTime.getTimeString(), camera.getPosition().getMinX()+100, camera.getPosition().getMinY()+150);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_F5)) {
			nextLevel(game);
		}
		
		// if the goal is reached
		if (Math.abs(player.getCenterX()-goalLoc.x) < 30 && Math.abs(player.getCenterY() - goalLoc.y) < 30) {
			if (lastTime == null) lastTime = new Time();
			lastTime.set(timer.getMillis());
			if (bestTime == null) bestTime = new Time();
			if ((lastTime.getMillis() < bestTime.getMillis()) || (bestTime.getMillis() == 0)) {
				bestTime.set(timer.getMillis());
			}
			nextLevel(game);
		}
		
		// restart if the player falls into a pit
//		if (player.getY() > map.getHeight()+64) game.enterState(this.getID());   
		
		// update world
		world.step(delta/1000f, Config.VELOCITY_ITERATIONS, Config.POSITION_ITERATIONS);
		
		// update player
		player.update(gc, delta);
		
		// update enemies
		for (Iterator<Enemy> it = enemies.iterator(); it.hasNext(); ) {
			Enemy e = it.next();
			if (e.getHp() > 0) {
				e.update(gc, delta);
			} else {
				it.remove();
				e.kill();
			}
		}

		// check toggles
		if (gc.getInput().isKeyPressed(Input.KEY_F3)) viewDebug = !viewDebug;
		if (gc.getInput().isKeyPressed(Input.KEY_F4)) godMode = !godMode;
		if (gc.getInput().isKeyPressed(Input.KEY_F11)) gc.setFullscreen(!gc.isFullscreen());
		
		camera.centerOn(player.getX(),player.getY());
		cursor.update(gc, delta);
		timer.update(delta);
	}
	
	private void nextLevel(StateBasedGame game) {
		if (this.getID() == 4) game.enterState(0);
		else game.enterState((this.getID()+1) % 5);
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame game)
			throws SlickException {
		super.enter(gc, game);
		Controls.setGC(gc);
		
		world = new World(gravity);
		
		world.setContactListener(new CombatContact());
		
		background = new Image(backgroundString);
		
		map = new Map(mapString, world);
		map.parseMapObjects();
		background = background.getScaledCopy((float) map.getHeight()/ (float) background.getHeight());
		debugdraw = new Box2DDebugDraw();
		debugdraw.setFlags(DebugDraw.e_shapeBit | DebugDraw.e_jointBit | DebugDraw.e_centerOfMassBit);
		world.setDebugDraw(debugdraw);
				
		player = MainGame.player;
		player.addToWorld(world, map.getPlayerLoc().x, map.getPlayerLoc().y 
				+ (Config.TILE_HEIGHT / 2) - (Config.PLAYER_HEIGHT / 2)); // move up to avoid getting stuck in the ground
		player.reset();
		enemies = map.getEnemies();
		for (Enemy e : enemies) {
			e.addToWorld(world, e.getX(), e.getY());
		}
		
		goalLoc = map.getGoalLoc();
		camera = new Camera(gc, map.getTiledMap());
		cursor = new Cursor(player);
		
		if (lastTime == null) {
			lastTime = new Time();
		}
		
		if (bestTime == null) {
			bestTime = new Time();
		}
		
		if (timer != null) {
			timer.reset();
		} else {
			timer = new Timer();
		}
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	/**
	 * @return the camera
	 */
	public static Camera getCamera() {
		return camera;
	}
	
	// kinda janky, remove when paralaxing set up
	private void drawBackground(Graphics graphics) {
		int backgroundX = 0;
		while (backgroundX < map.getWidth()){
			graphics.drawImage(background,  backgroundX,  0);
			backgroundX += background.getWidth();
		}
	}

}
