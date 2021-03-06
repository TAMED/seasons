/**
 * 
 */
package config;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


/**
 * @author Mullings
 *
 */
public enum Section {
	FOREST_1("Forest_1.tmx", "forest3.png", 500, Biome.FOREST, "Move!"),
	FOREST_2("Forest_2.tmx", "forest3.png", 500, Biome.FOREST, "Jump!"),
	FOREST_2A("Forest_2a.tmx", "forest3.png", 1000, Biome.FOREST, "Hook!"),
	FOREST_3("Forest_3.tmx", "forest3.png", 3000, Biome.FOREST, "Bearina"),
	FOREST_4("Forest_4.tmx", "forest3.png", 9000, Biome.FOREST, "Bearable"),
	FOREST_5("Forest_5.tmx", "forest3.png", 2000, Biome.FOREST, "Unbearable"),
	FOREST_6("Forest_6.tmx", "forest3.png", 12000, Biome.FOREST, "Bearinger"),
	
	LAKE_1("Forest_2b.tmx", "mountainLake3.png", 5000, Biome.LAKE, "Release!"),
	LAKE_2("Forest_8.tmx", "mountainLake3.png", 17000, Biome.LAKE, "Bearant"),
	LAKE_3("lake1.tmx", "mountainLake3.png", 2000, Biome.LAKE, "Bear"),
	LAKE_4("Lake_2.tmx", "mountainLake3.png", 2000, Biome.LAKE, "Bearlake"),
	LAKE_5("Lake_4.tmx", "mountainLake3.png", 30000, Biome.LAKE, "Waterbear"),
	
	DESERT_1("Desert_1.tmx", "desert4.png", 5000, Biome.DESERT, "Drybear"),
	DESERT_2("Desert_2.tmx", "desert4.png", 22000, Biome.DESERT, "Hotbear"),
	DESERT_3("Desert_3.tmx", "desert4.png", 20000, Biome.DESERT, "Sunbear"),
	
	CANYON_1("Canyon_1.tmx", "canyon2.png", 4000, Biome.CANYON, "Canbearyon"),
	CANYON_2("Canyon_3.tmx", "canyon2.png", 16000, Biome.CANYON, "Bearshrooms"),
	CANYON_3("Canyon_Climb.tmx", "canyon2.png", 30000, Biome.CANYON, "Flybear"),
	
	HELL_1("Hell_1.tmx", "hell2.png", 2000, Biome.HELL, "Bearvents"),
	HELL_2("Hell_2.tmx", "hell2.png", 11000, Biome.HELL, "Hellbear"), 
	HELL_3("Hell_3.tmx", "hell2.png", 12000, Biome.HELL, "Vulcanbear"),  ;

	private final String mapName;
	private final String backgroundName;
	private final int goalTime;
	private Biome biome;
	private String displayName;
	private Image instructionImg;
	private int instructionX;
	private int instructionY;
	
	static {
	}
	
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
		return Config.MAP_PATH + biome.getName() + "/" + mapName;
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
	
	
	public void setInstruction(Image image, int x, int y) {
		instructionImg = image;
		instructionX = x;
		instructionY = y;
	}
	
	public void renderInstruction(Graphics g) {
		g.drawImage(instructionImg, instructionX, instructionY);
	}
	
	public boolean hasInstruction() {
		return instructionImg != null;
	}
}
