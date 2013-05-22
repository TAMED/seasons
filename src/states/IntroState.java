package states;

import java.awt.Color;
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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import config.Config;
import config.Level;

public class IntroState extends BasicGameState{
	public static final int ID = 1;
	private UnicodeFont font;
	private Image background;
	private ArrayList<SectionWidget> sections = new ArrayList<SectionWidget>();
	
	public IntroState() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		font = Config.MENU_FONT;
        ((List<Effect>) font.getEffects()).add(new ColorEffect(Color.WHITE));
        background = new Image("assets/backgrounds/menuA.png");
        
		for (int i = 0; i < Level.values().length; i++) {
			for (int j = 0; j < Level.values()[i].getNumSections(); j++) {
				SectionWidget sw = new SectionWidget(gc, Level.values()[i], j, game);
				sections.add(sw);
				System.out.println(sw.getSection().getDisplayName());
			}			
		}

	}

	@Override
	public void enter(GameContainer gc, StateBasedGame game)
			throws SlickException {
		super.enter(gc, game);
		Level.clearQueue();
		ResultsState.clearResults();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		
		background.draw(0, 0, Config.RESOLUTION_WIDTH, Config.RESOLUTION_HEIGHT);
		
		for (int i = 0; i < sections.size(); i++) {
				sections.get(i).render(gc, g);
				sections.get(i).getSalmonSprite().render(g);
		}
		
		String title = "Level Select";
		int w = Config.RESOLUTION_WIDTH;
		FontUtils.drawCenter(font, title, 0, 0, w);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		for (int i = 0; i < sections.size(); i++) {
			sections.get(i).update(gc, delta);
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
