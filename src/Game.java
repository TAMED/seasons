import input.Controls;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import map.Map;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ui.Cursor;
import util.Box2DDebugDraw;
import camera.Camera;

import combat.CombatContact;

import config.Config;
import entities.Player;
import entities.enemies.Enemy;
import entities.enemies.Ent;

/**
 * 
 */

/**
 * @author Mullings
 *
 */
public class Game extends BasicGame {
	/**
	 *  This is only here for testing pusposes
	 */
	private Map testMap;
	private Vec2 gravity;
	private World testWorld;
	private int velocityIterations = 6;
	private int positionIterations = 2;
	private Player player;
	private ArrayList<Enemy> enemies;
	private Box2DDebugDraw debugdraw;
	private boolean viewDebug = false;
	private static Camera camera;
	private Cursor cursor;

	/**
	 * @param title
	 */
	public Game(String title) {
		super(title);
	}
	
	// TODO this is temporary: should be moved to a separate class, use constants, etc.
	public static void main(String[] arguments) {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.matches(".*linux.*")) {
			System.setProperty( "java.library.path", "lib/linux/" );
		} else if (os.matches(".*windows.*")) {
			System.setProperty( "java.library.path", "lib\\windows\\" );
		} else if (os.matches(".*mac.*")) {
			System.setProperty( "java.library.path", "lib/macosx/" );
		} else {
			System.out.println("Could not identify operating system: " + os);
			System.exit(1);
		}
		
		try {
			Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
			fieldSysPath.setAccessible( true );
			fieldSysPath.set( null, null );
			
			AppGameContainer app = new AppGameContainer(new Game("Seasons"));
			app.setDisplayMode(1024, 768, false);
			app.setTargetFrameRate(60);
			app.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, Graphics graphics) throws SlickException {
		//testMap.render();
		camera.drawMap();
		camera.translateGraphics();
		if (viewDebug) {
			debugdraw.setGraphics(graphics);
			testWorld.drawDebugData();
		}
		player.render(graphics);
		for (Enemy e : enemies) {
			e.render(graphics);			
		}
		
		cursor.render(graphics);
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {
		Controls.setGC(gc);
		
		gravity = new Vec2(0,Config.GRAVITY);
		testWorld = new World(gravity);
		
		testWorld.setContactListener(new CombatContact());
		
		testMap = new Map("assets/maps/slopetest.tmx", testWorld);
		testMap.parseMapObjects();		
		
		debugdraw = new Box2DDebugDraw();
		debugdraw.setFlags(DebugDraw.e_shapeBit);
		testWorld.setDebugDraw(debugdraw);
		
		player = new Player(400, 100, 32, 72);
		player.getPhysicsBodyDef().allowSleep = false;
		player.addToWorld(testWorld);
		
		enemies = new ArrayList<Enemy>();
		Enemy ent1 = new Ent(900, 600);
		ent1.addToWorld(testWorld);
		enemies.add(ent1);
		
		camera = new Camera(gc, testMap.getTiledMap());
		Config.camera = camera;
		
		cursor = new Cursor(player);
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) init(gc);
		if (player.getHp() <= 0) init(gc);
		
		testWorld.step(delta/1000f, velocityIterations, positionIterations);
		player.update(gc, delta);
		
		for (Iterator<Enemy> it = enemies.iterator(); it.hasNext(); ) {
			Enemy e = it.next();
			if (e.getHp() > 0) {
				e.update(gc, delta);
			} else {
				it.remove();
				e.kill();
			}
		}

		if (gc.getInput().isKeyPressed(Input.KEY_F3)) viewDebug = !viewDebug;
		camera.centerOn(player.getX(),player.getY());
		
		cursor.update(gc, delta);
	}

	/**
	 * @return the camera
	 */
	public static Camera getCamera() {
		return camera;
	}

}
