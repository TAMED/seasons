package map;

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

	public void parseMapObjects(){
		for(int j = 0; j < foreground.getHeight(); j++) {
			for(int i = 0; i < foreground.getWidth(); i++) {
				// other functions may be added here, currently
				// only parses walls
				parseTileBox2D(i,j);
			}
		}
	}
	
	public TiledMap getTiledMap() {
		return foreground;
	}
	
	private void parseTileBox2D(int i, int j) {
		int tileId = foreground.getTileId(i, j, 0);
		String tileType = foreground.getTileProperty(tileId, "type", "meh");
		for(int k = 0; k < Config.WALL_IDS.length; k++) {
			if (tileType.equals("wall")) {
				Box2DTile tile = new Box2DTile(i+(float).5,j+(float).5, foreground.getTileWidth(), foreground.getTileHeight());
				tile.addToWorld(world);
			}
		}	
	}
	
	public void render() {
		foreground.render(0, 0);
	}
}
