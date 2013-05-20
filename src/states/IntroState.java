package states;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import menu.SectionWidget;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import ui.Transitions;
import config.Config;
import config.Level;

public class IntroState extends BasicGameState{
	private static final int KEYCODE_NUMBER_OFFSET = 2;
	public static final int ID = 0;
	private UnicodeFont font;
	private Image background;
	private ArrayList<SectionWidget> sections = new ArrayList<SectionWidget>();
	
	public IntroState() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		font = new UnicodeFont(new Font("", Font.PLAIN,30));
        font.addAsciiGlyphs();
        ((List<Effect>) font.getEffects()).add(new ColorEffect(Color.WHITE));
        font.loadGlyphs();
        background = new Image("assets/backgrounds/menuA.png");
		Image image = new Image("assets/images/nonentities/salmon/salmon.png");
		for (int i = 0; i < 4; i++) {
			SectionWidget sw = new SectionWidget(gc, Level.FOREST, i);
			sections.add(sw);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		
		background.draw(0, 0, Config.RESOLUTION_WIDTH, Config.RESOLUTION_HEIGHT);
		
		for (int i=0;i<4;i++) {
			sections.get(i).render(gc, g);
			sections.get(i).getSalmonSprite().render(g);
		}
		
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
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		for (int i=0;i<4;i++) {
			sections.get(i).update(gc, delta);
		}
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
