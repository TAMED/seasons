package map;

public class LevelData {
	String map;
	String background;
	int id;
	float gravScale;
	
	public LevelData(String map, String background, int id) {
		this.map = map;
		this.background = background;
		this.id = id;
		this.gravScale = 1;
	}
	
	public LevelData(String map, String background, int id, float gravScale) {
		this.map = map;
		this.background = background;
		this.id = id;
		this.gravScale = gravScale;
	}
	
}
