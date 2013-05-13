/**
 * 
 */
package config;

import org.jbox2d.common.Vec2;

/**
 * @author Mullings
 *
 */
public enum Section {
	FOREST_1("Forest_1.tmx", "forest3.png"),
	FOREST_2("Forest_2.tmx", "forest3.png"),
	FOREST_3("Forest_3.tmx", "forest3.png"),
	FOREST_4("Forest_4.tmx", "forest3.png"),
	FOREST_5("Forest_5.tmx", "forest3.png"),
	FOREST_6("Forest_6.tmx", "forest3.png"),
	FOREST_7("Forest_7.tmx", "forest3.png"),
	FOREST_8("Forest_8.tmx", "forest3.png"),
	
	FOREST_SEC1("1.tmx", "forest3.png"),
	FOREST_SEC2("2.tmx", "forest3.png"),
	FOREST_SEC3("3.tmx", "forest3.png"),
	FOREST_SEC4("4.tmx", "forest3.png"),
	FOREST_SEC5("5.tmx", "forest3.png"),
	FOREST_SEC6("6.tmx", "forest3.png"),
	FOREST_CLIFF("cliffForest.tmx", "forest3.png"),
	FOREST_VINES("entirelyVines.tmx", "forest3.png"),
	FOREST_HILLS("moreHillyForest.tmx", "forest3.png"),
	FOREST_LONG("longMap.tmx", "forest3.png"),
	LAKE_UNDERWATER("lake1.tmx", "mountainLake3.png"),
	FOREST_PLAINS("plains.tmx", "forest3.png");

	private final String mapName;
	private final String backgroundName;
	private final Vec2 gravity;
	
	private Section(String map, String background) {
		this(map, background, new Vec2(0, Config.GRAVITY));
	}
	
	private Section(String map, String background, Vec2 gravity) {
		this.mapName = map;
		this.backgroundName = background;
		this.gravity = gravity;
	}
	
	public String getMapPath() {
		return Config.MAP_PATH + mapName;
	}
	
	public String getBackgroundPath() {
		return Config.BACKGROUND_PATH + backgroundName;
	}
	
	public Vec2 getGravity() {
		return gravity;
	}
	
	public int getID() {
		return -ordinal() - 1;
	}
}
