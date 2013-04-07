package main;
import java.lang.reflect.Field;

import level.LevelState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import entities.Player;

/**
 * 
 */

/**
 * @author Mullings
 *
 */
public class MainGame extends StateBasedGame {
	public static Player player;
	
	
	/**
	 * @param title
	 */
	public MainGame(String title) {
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
			
			AppGameContainer app = new AppGameContainer(new MainGame("Seasons"));
			app.setDisplayMode(1024, 768, false);
			app.setTargetFrameRate(60);
			app.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		player = new Player(0, 0, 32, 72);
		player.getPhysicsBodyDef().allowSleep = false;
		addState(new LevelState("assets/maps/slopetest.tmx",0));
		
	}

}
