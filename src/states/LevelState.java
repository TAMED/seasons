package states;

import input.Controls;

import java.util.ArrayList;
import java.util.Iterator;

import main.MainGame;
import map.Map;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Vec2;
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
import entities.Salmon;
import entities.enemies.Enemy;

public class LevelState extends BasicGameState{
	private int id;
	private String mapString;
	private Map map;
	private Vec2 gravity;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Salmon> salmons;
	private static Box2DDebugDraw debugdraw;
	private boolean viewDebug = false;
	public static boolean godMode = false;
	private static Camera camera;
	private Cursor cursor;
	private Vec2 goalLoc;
	private String backgroundString;
	private Image background;
	
	private Time currentTime;
	private Time lastTime;
	private Time bestTime;
	private static Timer timer;
	
	static {
		debugdraw = new Box2DDebugDraw();
		debugdraw.setFlags(DebugDraw.e_shapeBit | DebugDraw.e_jointBit | DebugDraw.e_centerOfMassBit);
		timer = new Timer(100, 100);
	}
	
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
		timer.updateTime(currentTime, lastTime, bestTime);
		timer.render(graphics);
		camera.translateGraphics();
		
		
		if (viewDebug) {
			debugdraw.setGraphics(graphics);
			map.getWorld().drawDebugData();
		} else {
			player.render(graphics);
			for (Enemy e : enemies) {
				e.render(graphics);
			}
			for (Salmon s : salmons) {
				s.render(graphics);
			}
		}
		cursor.render(graphics);
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
			lastTime.set(currentTime.getMillis());
			if (bestTime == null) bestTime = new Time(currentTime.getMillis());
			else if (lastTime.getMillis() < bestTime.getMillis()) {
				bestTime.set(currentTime.getMillis());
			}
			nextLevel(game);
		}
		
		// restart if the player falls into a pit
//		if (player.getY() > map.getHeight()+64) game.enterState(this.getID());   
		
		// update world
		map.getWorld().step(delta/1000f, Config.VELOCITY_ITERATIONS, Config.POSITION_ITERATIONS);
		
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
		
		for (Salmon s : salmons) {
			s.update(gc, delta);
		}

		// check toggles
		if (gc.getInput().isKeyPressed(Input.KEY_F3)) viewDebug = !viewDebug;
		if (gc.getInput().isKeyPressed(Input.KEY_F4)) godMode = !godMode;
		if (gc.getInput().isKeyPressed(Input.KEY_F11)) gc.setFullscreen(!gc.isFullscreen());
		
		camera.centerOn(player.getX(),player.getY());
		cursor.update(gc, delta);
		currentTime.update(delta);
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
		
		map = new Map(mapString, gravity);
		map.parseMapObjects();
		map.getWorld().setContactListener(new CombatContact());
		map.getWorld().setDebugDraw(debugdraw);
		
		background = new Image(backgroundString);
		background = background.getScaledCopy((float) map.getHeight()/ (float) background.getHeight());

		player = MainGame.player;
		player.addToWorld(map.getWorld(), map.getPlayerLoc().x, map.getPlayerLoc().y 
				+ (Config.TILE_HEIGHT / 2) - (Config.PLAYER_HEIGHT / 2)); // move up to avoid getting stuck in the ground
		player.reset();
		
		currentTime = new Time();
		
		enemies = map.getEnemies();
		for (Enemy e : enemies) {
			e.addToWorld(map.getWorld(), e.getX(), e.getY());
		}
		
		salmons = map.getSalmons();
		for (Salmon s : salmons) {
			s.addToWorld(map.getWorld(), s.getX(), s.getY(), currentTime);
		}
		
		goalLoc = map.getGoalLoc();
		camera = new Camera(gc, map.getTiledMap());
		cursor = new Cursor(player);
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
