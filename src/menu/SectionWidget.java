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
import time.Timer;
import ui.Transitions;
import config.Config;
import config.Level;
import config.Section;
import entities.Salmon;
import entities.Sprite;
import states.ResultsState;
import states.ResultsState.Grade;

public class SectionWidget {
	private Salmon salmonSprite;
	MouseOverArea mouseOver;
	private Section section;
	private final int WIDTH = Config.RESOLUTION_WIDTH/5;
	private final int HEIGHT = 60;
	private final int MARGIN_TOP = 100;
	private final int PADDING = 5;
	private final int MARGIN_LEFT = 20;
	private UnicodeFont font;
	private int x;
	private int y;
	private String displayName;
	private float opacity;
	private boolean locked = true;
	private Section prevSection;
	private Grade grade;
	private int gradeX = 120;
	private int gradeY = 30;
	private float gradeScale = .6f;
	
	private SoundEffect btnSound;
	private Level level;
	
	public SectionWidget(GUIContext container, final Level level, final StateBasedGame game) {
		btnSound = new SoundEffect("assets/sounds/Jump_Sound.wav");
		x = level.ordinal() * WIDTH - 20;
		y = MARGIN_TOP;
		
		Image image;
		font = Config.MENU_FONT;
        this.level = level;
        try {
			salmonSprite = new Salmon(x + 4*PADDING + MARGIN_LEFT, y + 4*PADDING);
		} catch (SlickException e) {
			e.printStackTrace();
		}
        
        try {
			image = new Image(0,0);
			mouseOver = new MouseOverArea(container, image, x, y, WIDTH, HEIGHT, new ComponentListener() {
				public void componentActivated(AbstractComponent source) {
					Level.addToQueue(level);
					game.enterState(Level.getNextSection().getID(), Transitions.fadeOut(), Transitions.fadeIn());
				}
			});
		} catch (SlickException e) {
			e.printStackTrace();
		}
        
        this.displayName = "ALL " + level.toString();
        this.opacity = .9f;
        
        int levelIndex = level.ordinal();
        
        if (levelIndex == 0) {
        	this.locked = false;
        } else {
        	Level prevLevel = Level.values()[levelIndex-1];
        	this.prevSection = prevLevel.getSection(prevLevel.getNumSections()-1);
        	Timer t = Config.times.get(prevSection);
        	if (t != null && t.getBestTime().getMillis() < Integer.MAX_VALUE) {
            	this.locked = false;
            }
        }
	}
	
	public SectionWidget(GUIContext container, final Level level, final int index, final StateBasedGame game) {
		btnSound = new SoundEffect("assets/sounds/Jump_Sound.wav");
		x = level.ordinal() * WIDTH;
		y = (index+1) * HEIGHT + MARGIN_TOP + PADDING * (index+1);
		
		Image image;
		font = Config.MENU_FONT;
		this.level = level;
		
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
					Level.addToQueue(level.getSection(index));
					game.enterState(Level.getNextSection().getID(), Transitions.fadeOut(), Transitions.fadeIn());
				}
			});
		} catch (SlickException e) {
			e.printStackTrace();
		}
        
        this.displayName = Integer.toString(index + 1) + ": " + section.getDisplayName();
        this.opacity = .6f;
        
        int levelIndex = level.ordinal();
        
        // forest case
        if (levelIndex == 0) {
        	if (index == 0) {
        		this.locked = false;
        	} else {
            	this.prevSection = level.getSection(index-1);
            	Timer t = Config.times.get(prevSection);
            	if (t != null && t.getBestTime().getMillis() < Integer.MAX_VALUE) {
                	this.locked = false;
                } 
        	}
        } else if (index == 0) {// first section of every other level 
        	Level prevLevel = Level.values()[levelIndex-1];
        	this.prevSection = prevLevel.getSection(prevLevel.getNumSections()-1);
        	Timer t = Config.times.get(prevSection);
        	if (t != null && t.getBestTime().getMillis() < Integer.MAX_VALUE) {
            	this.locked = false;
            } 
        }
        
        else {
        	this.prevSection = level.getSection(index-1);
        	Timer t = Config.times.get(prevSection);
        	if (t != null && t.getBestTime().getMillis() < Integer.MAX_VALUE) {
            	this.locked = false;
            }
        }
        
	}

	
	
	public void render(GameContainer gc, Graphics g) {
		font.drawString((float)x + PADDING + MARGIN_LEFT + 40, (float)y , displayName, new org.newdawn.slick.Color(1,1,1, opacity));
		String bestTime;
		if (this.section == null) {
			bestTime = getBestTime(this.level);
		} else {
			bestTime = getBestTime(this.section);
			if (getBestTimeInt(this.section) != Integer.MAX_VALUE) {
				grade = ResultsState.getGrade(getBestTimeInt(this.section), this.section.getGoalTime());
				grade.getImage().draw((float)x + gradeX, (float)y + gradeY, gradeScale);
			}
		}
		Config.TIME_FONT.drawString((float)x + PADDING + MARGIN_LEFT + 50, (float)y + 30, bestTime, new org.newdawn.slick.Color(1,1,1, opacity));
		
		mouseOver.render(gc, g);
		salmonSprite.display(!locked);
		if (locked) {
			mouseOver.setAcceptingInput(false);

		} else {
			mouseOver.setAcceptingInput(true);
			float alpha = mouseOver.isMouseOver() ? 1.0f : opacity;
			if (mouseOver.isMouseOver() && salmonSprite.isStopped()) {
				btnSound.play();
				salmonSprite.play();
			} else if (!mouseOver.isMouseOver()) {
				salmonSprite.stop();
			}
			font.drawString((float)x + PADDING + MARGIN_LEFT + 40, (float)y , displayName, new org.newdawn.slick.Color(1,1,1, alpha));
		}
		


	}
	
	private String getBestTime(Section s) {
		String best = Config.times.get(section).getBestTime().getTimeString();
		return best;
	}
	
	private int getBestTimeInt(Section s) {
		return Config.times.get(s).getBestTime().getMillis();
	}
	
	private String getBestTime(Level l) {
//		int times = 0;
//		for (int i = 0; i < l.getNumSections(); i++) {
//			Section s = l.getSections()[i];
//			if (Config.times.get(section).getBestTime().getMillis() != Integer.MAX_VALUE) {
//				times += Config.times.get(s).getBestTime().getSeconds();
//			} else {
//				return "";
//			}
//		}
//		Time t = new Time(times);
//		return t.getTimeString();
		return "";
	}
	
	public void update(GameContainer gc, int delta) {
		salmonSprite.update(gc, delta);
		if (this.locked == true) {
			if (this.prevSection != null) {
				Timer t = Config.times.get(prevSection);
	        	if (t != null && t.getBestTime().getMillis() < Integer.MAX_VALUE) {
	        		this.locked = false;
	        	}
	        } 
		}
	}
	
	public Sprite getSalmonSprite() {
		return salmonSprite;
	}
	
	public Section getSection() {
		return section;
	}
}
