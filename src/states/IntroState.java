package states;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.Transitions;

import config.Level;

public class IntroState extends BasicGameState{
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
		String pressKey = "Left Click to Start";
		font.getWidth(title);
		font.drawString(gc.getWidth()/2 - font.getWidth(title)/2,gc.getHeight()/2 - font.getHeight(title)/2, title);
		font.drawString(gc.getWidth()/2 - font.getWidth(pressKey)/2,gc.getHeight()/2+font.getHeight(title), pressKey);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int arg2)
			throws SlickException {
		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			Level.FOREST_1.addToQueue();
			game.enterState(LevelState.sectionQueue.poll().getID(), Transitions.fadeOut(), Transitions.fadeIn());
		}
		
	}

	@Override
	public int getID() {
		return ID;
	}

}
