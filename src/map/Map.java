package map;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import config.Config;

public class Map {
	TiledMap foreground;
	World world;
	
	public Map(String tmxMap, World world) throws SlickException {
		foreground = new TiledMap(tmxMap);
		this.world = world;
	}
	public void parseMapObjects() {
		parseWallObjects();
	}
	/**
	 * Parses all wall objects 
	 * Will create horizontal and vertical lines around all blocks,
	 * which will allow player to move across them.
	 */
	public void parseWallObjects(){
		int height = foreground.getHeight();
		int width = foreground.getWidth();
		int tileHeight = foreground.getTileHeight();
		int tileWidth = foreground.getTileWidth();
		// create horizontal lines
		for(int j = 0; j < height; j++) {
			boolean running = false;
			Vec2 startVTop = new Vec2();
			Vec2 startVBottom = new Vec2();
			Vec2 endVTop = new Vec2();
			Vec2 endVBottom = new Vec2();
			for(int i = 0; i < width; i++) {
				int tileId = foreground.getTileId(i, j, 0);
				String tileType = foreground.getTileProperty(tileId, "type", "meh");
				// create the start of a line
				if (!running) {
					if(tileType.equals("wall")) {
						startVTop.set(i*tileWidth / Config.PIXELS_PER_METER, j*tileHeight / Config.PIXELS_PER_METER);
						startVBottom.set(i*tileWidth / Config.PIXELS_PER_METER, (j+1)*tileHeight / Config.PIXELS_PER_METER);
						System.out.println(j);
						running = true;
					}
				}
				// end a line if a blank block is reached or end of map
				if (running) {
					if(!tileType.equals("wall") || i == width-1) {
						if(!tileType.equals("wall")) {
							endVTop.set(i*tileWidth / Config.PIXELS_PER_METER, j*tileHeight / Config.PIXELS_PER_METER);
							endVBottom.set(i*tileWidth / Config.PIXELS_PER_METER, (j+1)*tileHeight / Config.PIXELS_PER_METER);
						}
						// extend line a bit if end of map
						else if(i == width-1) {
							endVTop.set((i+1)*tileWidth / Config.PIXELS_PER_METER, j*tileHeight / Config.PIXELS_PER_METER);
							endVBottom.set((i+1)*tileWidth / Config.PIXELS_PER_METER, (j+1)*tileHeight / Config.PIXELS_PER_METER);
						}
						// add lines to map
						EdgeShape edgeTop = new EdgeShape();
						EdgeShape edgeBottom = new EdgeShape();
						edgeTop.set(startVTop, endVTop);
						edgeBottom.set(startVBottom, endVBottom);
						BodyDef lineDef = new BodyDef();
						Body line = world.createBody(lineDef);
						line.createFixture(edgeTop, Config.DEFAULT_DENSITY);
						line.createFixture(edgeBottom, Config.DEFAULT_DENSITY);
						running = false;
					}
				}
			}
		}
		// create vertical lines
		for(int j = 0; j < width; j++) {
			boolean running = false;
			Vec2 startVTop = new Vec2();
			Vec2 startVBottom = new Vec2();
			Vec2 endVTop = new Vec2();
			Vec2 endVBottom = new Vec2();
			for(int i = 0; i < height; i++) {
				int tileId = foreground.getTileId(j, i, 0);
				String tileType = foreground.getTileProperty(tileId, "type", "meh");
				// create the start of a line
				if (!running) {
					if(tileType.equals("wall")) {
						startVTop.set(j*tileWidth / Config.PIXELS_PER_METER, i*tileHeight / Config.PIXELS_PER_METER);
						startVBottom.set((j+1)*tileWidth / Config.PIXELS_PER_METER, i*tileHeight / Config.PIXELS_PER_METER);
						running = true;
					}
				}
				// end a line if a blank block is reached or end of map
				if (running) {
					if(!tileType.equals("wall") || i == height-1) {
						if(!tileType.equals("wall")) {
							endVTop.set(j*tileWidth / Config.PIXELS_PER_METER, i*tileHeight / Config.PIXELS_PER_METER);
							endVBottom.set((j+1)*tileWidth / Config.PIXELS_PER_METER, i*tileHeight / Config.PIXELS_PER_METER);
						}
						// extend line a bit if end of map
						else if(i == height-1) {
							endVTop.set(j*tileWidth / Config.PIXELS_PER_METER, (i+1)*tileHeight / Config.PIXELS_PER_METER);
							endVBottom.set((j+1)*tileWidth / Config.PIXELS_PER_METER, (i+1)*tileHeight / Config.PIXELS_PER_METER);
						}
						// add lines to map
						EdgeShape edgeTop = new EdgeShape();
						EdgeShape edgeBottom = new EdgeShape();
						edgeTop.set(startVTop, endVTop);
						edgeBottom.set(startVBottom, endVBottom);
						BodyDef lineDef = new BodyDef();
						Body line = world.createBody(lineDef);
						line.createFixture(edgeTop, Config.DEFAULT_DENSITY);
						line.createFixture(edgeBottom, Config.DEFAULT_DENSITY);
						running = false;
					}
				}
			}
		}
	}
	
	public TiledMap getTiledMap() {
		return foreground;
	}
	
	public void render() {
		foreground.render(0, 0);
	}
}
