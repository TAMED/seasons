/**
 * 
 */
package config;


/**
 * @author Mullings
 *
 */
public enum Section {
	FOREST_1("Forest_1.tmx", "forest3.png", 20000, Biome.FOREST, "Forest 1"),
	FOREST_2("Forest_2.tmx", "forest3.png", 20000, Biome.FOREST, "Forest 2"),
	FOREST_2A("Forest_2a.tmx", "forest3.png", 20000, Biome.FOREST, "Forest 3"),
	FOREST_2B("Forest_2b.tmx", "forest3.png", 20000, Biome.FOREST, "Forest 4"),
	FOREST_3("Forest_3.tmx", "forest3.png", 20000, Biome.FOREST, "Forest 5"),
	FOREST_4("Forest_4.tmx", "forest3.png", 20000, Biome.FOREST, "Forest 6"),
	FOREST_5("Forest_5.tmx", "forest3.png", 20000, Biome.FOREST, "Forest 7"),
	FOREST_6("Forest_6.tmx", "forest3.png", 20000, Biome.FOREST, "Forest 8"),
	FOREST_7("Forest_7.tmx", "forest3.png", 20000, Biome.FOREST, "Forest 9"),
	FOREST_8("Forest_8.tmx", "forest3.png", 20000, Biome.FOREST, "Forest 10"),
	
	LAKE_1("lake1.tmx", "mountainLake3.png", 20000, Biome.LAKE, "Lake 1"), ;
	//LAKE_2("Forest_11.tmx", "forest3.png");

	private final String mapName;
	private final String backgroundName;
	private final int goalTime;
	private Biome biome;
	private String displayName;
	
	private Section(String map, String background) {
		this(map, background, 20000, Biome.FOREST, "Make a name!");
	}
	
	private Section(String map, String background, int goalTime, Biome biome, String displayName) {
		this.mapName = map;
		this.backgroundName = background;
		this.goalTime = goalTime;
		this.biome = biome;
		this.displayName = displayName;
	}
	
	public String getMapPath() {
		return Config.MAP_PATH + mapName;
	}
	
	public String getMapName() {
		return mapName;
	}
	
	public String getDisplayName() {
		return displayName;
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
	public Biome getBiome() {
		return biome;
	}
}
