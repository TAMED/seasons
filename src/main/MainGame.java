package main;
import java.lang.reflect.Field;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.IntroState;
import states.LevelState;
import config.Config;
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
	public static ScalableGame game;
	
	public MainGame(String title) {
		super(title);
	}
	
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
			game = new ScalableGame(new MainGame("Seasons"), Config.RESOLUTION_WIDTH, Config.RESOLUTION_HEIGHT);
			AppGameContainer app = new AppGameContainer(game);
			setFullscreen(app, false);
			app.setVSync(true);
			app.setTargetFrameRate(60);
			app.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		player = new Player(Config.PLAYER_WIDTH, Config.PLAYER_HEIGHT, Config.PLAYER_GROUND);
		player.setDrawWidth(Config.PLAYER_DRAW_WIDTH);
		addState(new IntroState(0));
		addState(new LevelState("assets/maps/cliffForest.tmx", "assets/backgrounds/forest3.png", 3, new Vec2(0, Config.GRAVITY)));
		addState(new LevelState("assets/maps/entirelyVines.tmx", "assets/backgrounds/forest3.png", 2));
		addState(new LevelState("assets/maps/moreHillyForest.tmx", "assets/backgrounds/forest3.png", 1));
		addState(new LevelState("assets/maps/longMap.tmx", "assets/backgrounds/forest3.png", 4));
	}
	
	public static void setFullscreen(AppGameContainer app, boolean fullscreen) throws SlickException {
		if (fullscreen) {
			app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
		} else {
			app.setDisplayMode(Config.RESOLUTION_WIDTH, Config.RESOLUTION_HEIGHT, false);
		}
	}

}
