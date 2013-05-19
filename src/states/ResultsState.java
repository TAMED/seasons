package states;

import input.Controls;
import input.Controls.Action;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.Transitions;

import config.Config;
import config.Section;

public class ResultsState extends BasicGameState {
	private enum Grade { S, A, B, C };

	public static final int ID = 2;
	private List<Grade> grades;

	public ResultsState() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		grades = new LinkedList<Grade>();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics) throws SlickException {
		graphics.setColor(Color.white);
		int x = 100;
		int y = 100;
		for (int i = 0; i < grades.size(); i++) {
			Section s = LevelState.completedSections.get(i);
			String time = String.format("%.2f", Config.times.get(s).getLastTime().getMillis() / 1000f);
			String grade = grades.get(i).name();
			graphics.drawString(s.name() + " - " + time + " - " + grade, x, y);
			y += 100;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Controls.update(gc);
		if (Controls.isKeyPressed(Action.FIRE)) {
			System.out.println("2");
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
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
		super.leave(gc, game);
		LevelState.completedSections.clear();
	}
	
	private Grade getGrade(int time, int goal) {
		if (time <= goal) return Grade.S;
		else if (time <= 1.2 * goal) return Grade.A;
		else if (time <= 1.5 * goal) return Grade.B;
		else return Grade.C;
	}

	@Override
	public int getID() {
		return ID;
	}

}
