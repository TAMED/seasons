package states;

import input.Controls;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import anim.AnimationState;

import config.Config;
import entities.BearSprite;
import entities.Player;
import entities.Salmon;

public class TitleState extends BasicGameState {
	
	private Image name;
	private Salmon salmon;
	private BearSprite bear;
	private float jigglin = 0;

	public TitleState() {
		  
        try {
			salmon = new Salmon(800, 500);
			salmon.setDrawWidth(130);
			salmon.setDrawHeight(130);
			bear = new BearSprite(550, 500, 150, 150);
		} catch (SlickException e) {
			e.printStackTrace();
		}
        
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		name = new Image("assets/images/ui/title screen/title.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics)
			throws SlickException {
		name.drawCentered(Config.RESOLUTION_WIDTH / 2, Config.RESOLUTION_HEIGHT / 2);
		
		FontUtils.drawCenter(Config.MENU_FONT, "Press any key to continue", 0, Config.RESOLUTION_HEIGHT * 3 / 4, Config.RESOLUTION_WIDTH);
	

		bear.render(graphics);
		salmon.render(graphics);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		Controls.update(gc);
		
		if (Controls.moveKeyPressed()) {
			game.enterState(1);
		}
		
		rotateTitle();
		jigglin += delta/100f;
		
		salmon.update(gc, delta);
		bear.update(gc, delta);
	}

	public void rotateTitle() {
		name.setRotation(25f *  (float) Math.cos(jigglin));
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
