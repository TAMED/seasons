/**
 * 
 */
package config;


/**
 * @author Mullings
 *
 */
public enum Section {
	FOREST_1("Forest_1.tmx", "forest3.png", 20000),
	FOREST_2("Forest_2.tmx", "forest3.png", 20000),
	FOREST_3("Forest_3.tmx", "forest3.png", 20000),
	FOREST_4("Forest_4.tmx", "forest3.png", 20000),
	FOREST_5("Forest_5.tmx", "forest3.png", 20000),
	FOREST_6("Forest_6.tmx", "forest3.png", 20000),
	FOREST_7("Forest_7.tmx", "forest3.png", 20000),
	FOREST_8("Forest_8.tmx", "forest3.png", 20000),
	
	LAKE_1("lake1.tmx", "mountainLake3.png", 20000);
	//LAKE_2("Forest_11.tmx", "forest3.png");

	private final String mapName;
	private final String backgroundName;
	private final int goalTime;
	
	private Section(String map, String background) {
		this(map, background, 20000);
	}
	
	private Section(String map, String background, int goalTime) {
		this.mapName = map;
		this.backgroundName = background;
		this.goalTime = goalTime;
	}
	
	public String getMapPath() {
		return Config.MAP_PATH + mapName;
	}
	
	public String getBackgroundPath() {
		return Config.BACKGROUND_PATH + backgroundName;
	}
	
	public int getGoalTime() {
		return goalTime;
	}
	
	public int getID() {
		return -ordinal() - 1;
	}
}
