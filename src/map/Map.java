package map;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import util.Corner;

import config.Config;

public class Map {
	TiledMap foreground;
	World world;
	private int height;
	private int width;
	private int tileHeight;
	private int tileWidth;
	
	public Map(String tmxMap, World world) throws SlickException {
		foreground = new TiledMap(tmxMap);
		this.world = world;
		height = foreground.getHeight();
		width = foreground.getWidth();
		tileHeight = foreground.getTileHeight();
		tileWidth = foreground.getTileWidth();
	}
	public void parseMapObjects() {
		parseWallObjects();
		parseSlopeObjects();
	}
	
	/**
	 * Create a triangle around each slope.
	 * Each slope must have a start and end point, 
	 * the start point being the one that is the base of the triangle
	 */
	private void parseSlopeObjects() {
		for(int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++) {
				int tileId = foreground.getTileId(i, j, 0);
				String tileType = foreground.getTileProperty(tileId, "type", "meh");
				// find the endpoint of all slopes...
				if(tileType.equals("slope") && foreground.getTileProperty(tileId, "endpoint", "meh").equals("end")) {
					/// then find the start point, and create line.
					SlopeReturn tileData = findSlopeStart(Integer.parseInt(foreground.getTileProperty(tileId, "angle", "0")),i, j);
					Vec2 v2 = getCorners(i,j)[tileData.corner.opposite().ordinal()];
					Vec2 v1 = getCorners(tileData.i,tileData.j)[tileData.corner.ordinal()];
					createLine(v1,v2);
					createLine(v2, new Vec2(v2.x, v1.y));
					createLine(v1, new Vec2(v2.x, v1.y));
				}
			}
		}
		
	}
	
	/**
	 * Parses all wall objects 
	 * Will create horizontal and vertical lines around all blocks,
	 * which will allow player to move across them.
	 */
	private void parseWallObjects(){
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
						createLine(startVTop, endVTop);
						createLine(startVBottom, endVBottom);
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
						createLine(startVTop, endVTop);
						createLine(startVBottom, endVBottom);
						running = false;
					}
				}
			}
		}
	}
	/**
	 * Given the end of a slope, find the start of a slope
	 * @param slope
	 * @param i
	 * @param j
	 * @return
	 */
	private SlopeReturn findSlopeStart(int slope, int i, int j) {
		int curi = i;
		int curj = j;
		Corner startDir = Corner.TOPLEFT;
		if (foreground.getTileProperty(foreground.getTileId(i-slope, j-1, 0), "endpoint", "meh").equals("start")){
			startDir = Corner.TOPLEFT;
			curi -= slope;
			curj -= 1;
		}
		else if (foreground.getTileProperty(foreground.getTileId(i+slope, j-1, 0), "endpoint", "meh").equals("start")){
			startDir = Corner.TOPRIGHT;
			curi += slope;
			curj -= 1;
		}
		else if (foreground.getTileProperty(foreground.getTileId(i-slope, j+1, 0), "endpoint", "meh").equals("start")){
			startDir = Corner.BOTTOMLEFT;
			curi -= slope;
			curj += 1;
		}
		else if (foreground.getTileProperty(foreground.getTileId(i+slope, j+1, 0), "endpoint", "meh").equals("start")){
			startDir = Corner.BOTTOMRIGHT;
			curi += slope;
			curj += 1;
		}
		else {
			throw new IllegalArgumentException("Invalid map, slope doesn't have start");
		}
		
		while(true) {
			int previ = curi;
			int prevj = curj;
			switch(startDir) {
				case TOPLEFT:
					curi -= slope;
					curj -= 1;
				case TOPRIGHT:
					curi += slope;
					curj -= 1;
				case BOTTOMLEFT:
					curi -= slope;
					curj += 1;
				case BOTTOMRIGHT:
					curi += slope;
					curj += 1;
			}
			if(!foreground.getTileProperty(foreground.getTileId(curi, curj, 0), "endpoint", "meh").equals("start")){
				return new SlopeReturn(previ, prevj, startDir);
			}
		}
	}
	
	private Vec2[] getCorners(int x, int y) {
		Vec2[] corners = new Vec2[4];
		corners[Corner.TOPLEFT.ordinal()] = new Vec2((x)*tileWidth / Config.PIXELS_PER_METER,
                (y)*tileHeight / Config.PIXELS_PER_METER);
		corners[Corner.TOPRIGHT.ordinal()] = new Vec2((x+1)*tileWidth / Config.PIXELS_PER_METER,
                (y)*tileHeight / Config.PIXELS_PER_METER);
		corners[Corner.BOTTOMLEFT.ordinal()] = new Vec2((x)*tileWidth / Config.PIXELS_PER_METER,
                (y+1f)*tileHeight / Config.PIXELS_PER_METER);
		corners[Corner.BOTTOMRIGHT.ordinal()] = new Vec2((x+1)*tileWidth / Config.PIXELS_PER_METER,
                (y+1f)*tileHeight / Config.PIXELS_PER_METER);
		return corners;
	}
	
	private void createLine(Vec2 v1, Vec2 v2) {
		EdgeShape edge = new EdgeShape();
		edge.set(v1, v2);
		BodyDef lineDef = new BodyDef();
		Body line = world.createBody(lineDef);
		line.createFixture(edge, Config.DEFAULT_DENSITY);
	}
	
	public TiledMap getTiledMap() {
		return foreground;
	}
	
	public void render() {
		foreground.render(0, 0);
	}
}
