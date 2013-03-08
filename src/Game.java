import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.collision.*;
import org.jbox2d.collision.shapes.PolygonShape;

/**
 * 
 */

/**
 * @author Mullings
 *
 */
public class Game extends BasicGame {
	/**
	 *  This is only here for testing pusposes
	 */
	private TiledMap testMap;
	Vec2 gravity;
	World testWorld;
	BodyDef testBody;
	Body groundBody;
	PolygonShape groundBox;
	BodyDef bodyDef;
	Body body;
	PolygonShape dynamicBox;
	FixtureDef fixtureDef;
	float timeStep = (float) (1.0/60.0);
	int velocityIterations = 6;
	int positionIterations = 2;
	Rectangle fallingBlock;
	Rectangle fixedBlock;

	/**
	 * @param title
	 */
	public Game(String title) {
		super(title);
	}
	
	// TODO this is temporary: should be moved to a separate class, use constants, etc.
	public static void main(String[] arguments) {
		try {
			AppGameContainer app = new AppGameContainer(new Game("Seasons"));
			app.setDisplayMode(1024, 768, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		testMap.render(0, 0);
		arg1.draw(fallingBlock);
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer arg0) throws SlickException {
		testMap = new TiledMap("assets/maps/test.tmx");
		gravity = new Vec2(0,10);
		testWorld = new World(gravity, true);
		
		testBody = new BodyDef();
		testBody.position.set(new Vec2(220,736));
		
		groundBody = testWorld.createBody(testBody);
		groundBox = new PolygonShape();
		groundBox.setAsBox(50, 10);
		groundBody.createFixture(groundBox,0);
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(new Vec2(220,200));
		body = testWorld.createBody(bodyDef);
		
		dynamicBox = new PolygonShape();
		dynamicBox.setAsBox(1, 1);
		fixtureDef = new FixtureDef();
		fixtureDef.shape = dynamicBox;
		fixtureDef.density = 1;
		fixtureDef.friction = (float) .3;
		body.createFixture(fixtureDef);
		
		fallingBlock =new Rectangle(body.getPosition().x, body.getPosition().y, 20, 20);
		//fallingBlock.setImageColor(255, 0,0);
		//fixedBlock.setImageColor(0, 255, 0);
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		testWorld.step(timeStep, velocityIterations, positionIterations);
		Vec2 position = body.getPosition();
		float angle = body.getAngle();
		fallingBlock.setCenterX(position.x);
		fallingBlock.setCenterY(position.y);
	}

}
