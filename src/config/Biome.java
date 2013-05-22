package config;

import org.newdawn.slick.Color;

public enum Biome {
	FOREST(new Color(76, 178, 76)),
	LAKE(new Color(185, 69, 201)),
	DESERT(new Color(200, 200, 200)),
	CANYON(new Color(200, 200, 200)),
	HELL(new Color(200, 200, 200));
	private Color color;
	
	private Biome(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}


}
