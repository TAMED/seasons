package ui;

import java.awt.Font;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;


import time.Time;
import time.Timer;

@SuppressWarnings("unchecked")
public class TimeBar {
	private GradientFill timeFill;
	private Rectangle timeShape;
	private final Vec2 timePos = new Vec2(20,20);
	private final float timeHeight = 30;
	private float timeWidth;
	private float timeDivide;
	private static UnicodeFont goalFont;
	private static UnicodeFont currentFont;
	
	static {
		goalFont = new UnicodeFont(new Font("", Font.PLAIN,16));
        goalFont.addAsciiGlyphs();
        ((List<Effect>) goalFont.getEffects()).add(new ColorEffect(java.awt.Color.WHITE));
        try {
			goalFont.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
        
        currentFont = new UnicodeFont(new Font("", Font.BOLD,16));
        currentFont.addAsciiGlyphs();
        ((List<Effect>) currentFont.getEffects()).add(new ColorEffect(java.awt.Color.WHITE));
        try {
			currentFont.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public TimeBar(GameContainer gc) {
		timeWidth = gc.getWidth() - 2*timePos.x;
		timeShape = new Rectangle(timePos.x, timePos.y, 0, timeHeight);
		timeFill = new GradientFill(timePos.x, timePos.y, new Color(32, 131, 153), (gc.getWidth() - timePos.x)/4, timePos.y,
                new Color(138, 217, 235), true);
	}

	public void render(GameContainer gc, Graphics graphics, Timer timer) {
		graphics.setColor(Color.white);

		String goalStr = getTimeString(timer.getGoal());
		graphics.fillRect(gc.getWidth()/2 - 1, timePos.y+timeHeight, 1, 20);
		goalFont.drawString(gc.getWidth()/2 - goalFont.getWidth("Goal")/2, timeHeight + timePos.y + 20, "Goal");
		goalFont.drawString(gc.getWidth()/2 - goalFont.getWidth(goalStr)/2, goalFont.getHeight("Goal")+timeHeight + timePos.y + 20, goalStr);
		
		if (timer.getBestTime().getMillis() < Integer.MAX_VALUE) {
			String bestStr = getTimeString(timer.getBestTime());
			float bestOffset = Math.max(Math.min(timer.getBestTime().getMillis()/timeDivide, timeWidth),0) + timePos.x +1;
			graphics.fillRect(bestOffset, timePos.y+timeHeight, 1, 20);
			goalFont.drawString(bestOffset - goalFont.getWidth("Best")/2, timeHeight + timePos.y + 20, "Best");
			goalFont.drawString(bestOffset - goalFont.getWidth(bestStr)/2, goalFont.getHeight("Best")+timeHeight + timePos.y + 20, bestStr);
		}
		
		graphics.setColor(Color.black);
		graphics.fillRect(timePos.x, timePos.y, timeWidth, timeHeight);
		timeShape.setWidth(Math.max(Math.min(timer.getCurrentTime().getMillis()/timeDivide, timeWidth),0));
		graphics.fill(timeShape, timeFill);
		
		String currentStr = "Time: "+getTimeString(timer.getCurrentTime());
		currentFont.drawString(timePos.x + 10, (timeHeight - currentFont.getHeight("Current"))/2+timePos.y, currentStr);
	}
	
	public void enter(GameContainer gc, StateBasedGame game, Timer timer) {
		timeDivide = 2*timer.getGoal().getMillis()/timeWidth;
	}
	
	private String getTimeString(Time t) {
		if (t == null || t.getMillis() == Integer.MAX_VALUE) return "N/A";
		return String.format("%.2f", t.getMillis() / 1000f);
	}

}
