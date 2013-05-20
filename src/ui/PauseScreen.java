/**
 * 
 */
package ui;

import input.Controls;
import input.Controls.Action;

import java.awt.Font;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import config.Config;
import entities.Sprite;

/**
 * @author Mullings
 *
 */
public class PauseScreen extends Sprite {
	private static final Color color = new Color(0, 0, 0, 128);
	private UnicodeFont font;

	@SuppressWarnings("unchecked")
	public PauseScreen() {
		super(0, 0, Config.RESOLUTION_WIDTH, Config.RESOLUTION_HEIGHT);
		font = new UnicodeFont(new Font("", Font.PLAIN,64));
        font.addAsciiGlyphs();
        ((List<Effect>) font.getEffects()).add(new ColorEffect(java.awt.Color.WHITE));
        try {
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(color);
		graphics.fillRect(0, 0, Config.RESOLUTION_WIDTH, Config.RESOLUTION_HEIGHT);

		int y = Config.RESOLUTION_HEIGHT / 2 - font.getLineHeight() / 2;
		FontUtils.drawCenter(font, "Paused", 0, y, Config.RESOLUTION_WIDTH);
	}

	// No longer an override because we need the param game to change states, if necessary
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		if (Controls.isKeyPressed(Action.PAUSE)) {
			unpause(gc);
		} else if (Controls.isKeyPressed(Action.MENU)) {
			unpause(gc);
			game.enterState(0);
		} else if (Controls.isKeyPressed(Action.QUIT)) {
			gc.exit();
		}
	}

	private void unpause(GameContainer gc) {
		gc.setPaused(false);
		gc.setTargetFrameRate(Config.ACTIVE_FRAME_RATE);
	}

}
