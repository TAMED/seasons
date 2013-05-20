package menu;

import java.awt.Font;
import java.util.List;
import java.awt.Color;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.Effect;

import config.Config;
import config.Level;
import entities.Salmon;
import entities.Sprite;

public class SectionWidget {
	private Salmon salmonSprite;
	MouseOverArea mouseOver;
	private final int WIDTH = Config.RESOLUTION_WIDTH/5;
	private final int HEIGHT = 50;
	private final int MARGIN_TOP = 50;
	private final int PADDING = 5;
	private UnicodeFont font;
	private ColorEffect alpha = new ColorEffect();
	private int x;
	private int y;
	
	@SuppressWarnings("unchecked")
	public SectionWidget(GUIContext container, Level level, final int index) {
		x = level.ordinal() * WIDTH;
		y = index * HEIGHT + MARGIN_TOP + PADDING * index;
		
		Image image;
		try {
			image = new Image(0,0);
			mouseOver = new MouseOverArea(container, image, x, y, WIDTH, HEIGHT, new ComponentListener() {
				public void componentActivated(AbstractComponent source) {
					System.out.println(index);
				}
			});
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		font = new UnicodeFont(new Font("", Font.PLAIN,30));
        font.addAsciiGlyphs();
        alpha.setColor(new Color(1, 1, 1, 0.8f));

        ((List<Effect>) font.getEffects()).add(alpha);
        try {
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
        
        try {
			salmonSprite = new Salmon(x + 4*PADDING, y + 4*PADDING);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	
	
	public void render(GameContainer gc, Graphics g) {
		mouseOver.render(gc, g);
		float alpha = mouseOver.isMouseOver() ? 0.8f : 1.0f;
		if (mouseOver.isMouseOver() && salmonSprite.isStopped()) {
			salmonSprite.play();
		} else if (!mouseOver.isMouseOver()) {
			salmonSprite.stop();
		}

		
		font.drawString((float)x + PADDING + 40, (float)y , "Forest 1", new org.newdawn.slick.Color(1,1,1, alpha));
		
	}
	
	public void update(GameContainer gc, int delta) {
		salmonSprite.update(gc, delta);
	}
	
	public Sprite getSalmonSprite() {
		return salmonSprite;
	}
}
