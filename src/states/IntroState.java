package states;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import ui.Transitions;
import config.Config;
import config.Level;

public class IntroState extends BasicGameState{
	private static final int KEYCODE_NUMBER_OFFSET = 2;
	public static final int ID = 0;
	UnicodeFont font;
	
	public IntroState() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		font = new UnicodeFont(new Font("", Font.PLAIN,30));
        font.addAsciiGlyphs();
        ((List<Effect>) font.getEffects()).add(new ColorEffect(Color.WHITE));
        font.loadGlyphs();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		String title = "Scare Bear: 5 Seasons of Hell";
		String pressKey = "Use the number keys to select a level";
		int w = Config.RESOLUTION_WIDTH;
		int lh = font.getLineHeight();
		int y = Config.RESOLUTION_HEIGHT / 2 - lh;
		FontUtils.drawCenter(font, title, 0, y, w);
		y += lh;
		FontUtils.drawCenter(font, pressKey, 0, y, w);
		for (int i = 0; i < Level.values().length; i++) {
			y += lh;
			String str = (i+1) + ": " + Level.values()[i].name();
			FontUtils.drawCenter(font, str, 0, y, w);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int arg2)
			throws SlickException {
		for (int i = 0; i < Level.values().length; i++) {
			if(gc.getInput().isKeyPressed(KEYCODE_NUMBER_OFFSET + i)) {
				Level.values()[i].addToQueue();
				game.enterState(LevelState.sectionQueue.poll().getID(), Transitions.fadeOut(), Transitions.fadeIn());
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
