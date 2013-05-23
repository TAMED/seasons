package menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

import sounds.SoundEffect;
import ui.Transitions;
import config.Config;
import config.Level;
import config.Section;
import entities.Salmon;
import entities.Sprite;

public class SectionWidget {
	private Salmon salmonSprite;
	MouseOverArea mouseOver;
	private Section section;
	private final int WIDTH = Config.RESOLUTION_WIDTH/5;
	private final int HEIGHT = 50;
	private final int MARGIN_TOP = 100;
	private final int PADDING = 5;
	private final int MARGIN_LEFT = 20;
	private UnicodeFont font;
	private int x;
	private int y;
	
	private SoundEffect btnSound;
	
	public SectionWidget(GUIContext container, final Level level, final int index, final StateBasedGame game) {
		
		btnSound = new SoundEffect("assets/sounds/Jump_Sound.wav");
		x = level.ordinal() * WIDTH;
		y = index * HEIGHT + MARGIN_TOP + PADDING * index;
		
		Image image;
		font = Config.MENU_FONT;
        
        try {
			salmonSprite = new Salmon(x + 4*PADDING + MARGIN_LEFT, y + 4*PADDING);
		} catch (SlickException e) {
			e.printStackTrace();
		}
        
        this.section = level.getSection(index);
        try {
			image = new Image(0,0);
			mouseOver = new MouseOverArea(container, image, x, y, WIDTH, HEIGHT, new ComponentListener() {
				public void componentActivated(AbstractComponent source) {
					Level.addToQueue(level, index);
					game.enterState(Level.getNextSection().getID(), Transitions.fadeOut(), Transitions.fadeIn());
				}
			});
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	
	
	public void render(GameContainer gc, Graphics g) {
		mouseOver.render(gc, g);
		float alpha = mouseOver.isMouseOver() ? 0.8f : 1.0f;
		if (mouseOver.isMouseOver() && salmonSprite.isStopped()) {
			salmonSprite.play();
			btnSound.play();
		} else if (!mouseOver.isMouseOver()) {
			salmonSprite.stop();
		}

		font.drawString((float)x + PADDING + MARGIN_LEFT + 40, (float)y , section.getDisplayName(), new org.newdawn.slick.Color(1,1,1, alpha));
	}
	
	public void update(GameContainer gc, int delta) {
		salmonSprite.update(gc, delta);
	}
	
	public Sprite getSalmonSprite() {
		return salmonSprite;
	}
	
	public Section getSection() {
		return section;
	}
}
