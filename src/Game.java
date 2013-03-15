import java.lang.reflect.Field;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

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
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer arg0) throws SlickException {
		testMap = new TiledMap("assets/maps/test.tmx");
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub

	}

}
