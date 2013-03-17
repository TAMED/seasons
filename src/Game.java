import java.lang.reflect.Field;

import map.Box2DMap;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import util.Box2DDebugDraw;
import entities.Player;

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
	private TiledMap testMap;
	private Vec2 gravity;
	private World testWorld;
	private float timeStep = (float) (1.0/60.0);
	private int velocityIterations = 6;
	private int positionIterations = 2;
	private Player player;
	private Player ground;
	private Box2DDebugDraw debugdraw;

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
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		testMap.render(0, 0);
		debugdraw.setGraphics(arg1);
		testWorld.drawDebugData();
		player.render(arg1);
		ground.render(arg1);
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer arg0) throws SlickException {
		testMap = new TiledMap("assets/maps/test.tmx");
		gravity = new Vec2(0,10);
		testWorld = new World(gravity, true);
		
		debugdraw = new Box2DDebugDraw();
		debugdraw.setFlags(DebugDraw.e_shapeBit);
		testWorld.setDebugDraw(debugdraw);
		
		// tiles should eventually have their own class that's similar to Entity
		// but for now, it's a Player, whatever
		ground = new Player(400, 656, 32, 32);
		ground.getPhysicsBodyDef().type = BodyType.STATIC;
		ground.addToWorld(testWorld);
		
		player = new Player(400, 100, 32, 72);
		player.addToWorld(testWorld);
		
		Box2DMap blah = new Box2DMap(testMap, testWorld);
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		testWorld.step(timeStep, velocityIterations, positionIterations);
		player.update();
	}

}
