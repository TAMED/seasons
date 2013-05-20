package states;

import input.Controls;
import input.Controls.Action;

import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
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

import time.Timer;
import ui.Transitions;
import config.Config;
import config.Section;

public class ResultsState extends BasicGameState {
	private enum Grade { S, A, B, C };

	public static final int ID = 2;
	private static final int SPACING = 65;
	private static final float S_THRESHOLD = 1.0f;
	private static final float A_THRESHOLD = 1.25f;
	private static final float B_THRESHOLD = 2.0f;
	private List<Grade> grades;
	private UnicodeFont smallFont;
	private UnicodeFont bigFont;
	private Image background;
	
	@SuppressWarnings("unchecked")
	public ResultsState() throws SlickException {
		smallFont = new UnicodeFont(new Font("", Font.PLAIN, 30));
        smallFont.addAsciiGlyphs();
        ((List<Effect>) smallFont.getEffects()).add(new ColorEffect(java.awt.Color.WHITE));
        smallFont.loadGlyphs();

		bigFont = new UnicodeFont(new Font("", Font.PLAIN, 70));
        bigFont.addAsciiGlyphs();
        ((List<Effect>) bigFont.getEffects()).add(new ColorEffect(java.awt.Color.WHITE));
        bigFont.loadGlyphs();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		grades = new LinkedList<Grade>();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics) throws SlickException {
		graphics.setColor(Color.white);
		int sectionX = 350;
		int goalX = 500;
		int timeX = 650;
		int gradeX = 750;
		int bestX = 916;
		int bestGradeX = 1016;
		int y = (Config.RESOLUTION_HEIGHT / 2) - (SPACING * (grades.size() + 1) / 2);
		
		drawBackground(graphics);
		graphics.setColor(new Color(0, 0, 0, 128));
		graphics.fillRect(0, 0, Config.RESOLUTION_WIDTH, Config.RESOLUTION_HEIGHT);
		
		FontUtils.drawCenter(smallFont, "Section", sectionX, y, 0);
		FontUtils.drawCenter(smallFont, "Goal",    goalX,    y, 0);
		FontUtils.drawCenter(smallFont, "Time",    timeX,    y, 0);
		FontUtils.drawCenter(smallFont, "Rank",    gradeX,   y, 0);
		FontUtils.drawCenter(smallFont, "Best",    bestX,    y, 0);
		FontUtils.drawCenter(smallFont, "Rank",    bestGradeX,   y, 0);
		y += SPACING;
		
		for (int i = 0; i < grades.size(); i++) {
			Section s = LevelState.completedSections.get(i);
			Timer t = Config.times.get(s);
			String goal = String.format("%.2fs", s.getGoalTime() / 1000f);
			String time = String.format("%.2fs", t.getLastTime().getMillis() / 1000f);
			String grade = grades.get(i).name();
			String best = String.format("%.2fs", t.getBestTime().getMillis() / 1000f);
			String bestGrade = getGrade(t.getBestTime().getMillis(), t.getGoal().getMillis()).name();
			
			FontUtils.drawCenter(smallFont, s.name(),  sectionX,   y, 0);
			FontUtils.drawCenter(smallFont, goal,      goalX,      y, 0);
			FontUtils.drawCenter(smallFont, time,      timeX,      y, 0);
			FontUtils.drawCenter(bigFont,   grade,     gradeX,     y-30, 0);
			FontUtils.drawCenter(smallFont, best,      bestX,      y, 0);
			FontUtils.drawCenter(bigFont,   bestGrade, bestGradeX, y-30, 0);
			y += SPACING;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Controls.update(gc);
		if (Controls.isKeyPressed(Action.FIRE)) {
			game.enterState(IntroState.ID, Transitions.fadeOut(), Transitions.fadeIn());
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		grades.clear();
		for (Section s : LevelState.completedSections) {
			int time = Config.times.get(s).getLastTime().getMillis();
			grades.add(getGrade(time, s.getGoalTime()));
		}
		Section lastSection = LevelState.completedSections.get(LevelState.completedSections.size() - 1);
		background = new Image(lastSection.getBackgroundPath());
		background = background.getScaledCopy((float) Config.RESOLUTION_HEIGHT / background.getHeight());
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
		super.leave(gc, game);
		LevelState.completedSections.clear();
	}
	
	private Grade getGrade(int time, int goal) {
		if (time <= S_THRESHOLD * goal) return Grade.S;
		else if (time <= A_THRESHOLD * goal) return Grade.A;
		else if (time <= B_THRESHOLD * goal) return Grade.B;
		else return Grade.C;
	}
	
	private void drawBackground(Graphics graphics) {
		int backgroundX = 0;
		while (backgroundX < Config.RESOLUTION_WIDTH){
			graphics.drawImage(background,  backgroundX, 0);
			backgroundX += background.getWidth();
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
