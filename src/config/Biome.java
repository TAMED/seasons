package config;

import org.newdawn.slick.Color;

public enum Biome {
	FOREST(new Color(76, 178, 76)),
	LAKE(new Color(185, 69, 201));
	/*
	DESERT,
	CANYON,
	HELL;
	*/
	private Color color;
	
	private Biome(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}


}
