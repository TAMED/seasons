package config;

import org.newdawn.slick.Color;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public enum Biome {
	FOREST(new Color(76, 178, 76),   "Field19.ogg", "forest"),
	LAKE(  new Color(185, 69, 201),  "Field08.ogg", "lake"),
	DESERT(new Color(200, 200, 200), "Field12.ogg", "desert"),
	CANYON(new Color(200, 200, 200), "Field35.ogg", "canyon"),
	HELL(  new Color(200, 200, 200), "Field09.ogg", "hell");
	
	private Color color;
	private Music music;
	private String biomeName;

	
	private Biome(Color color, String music, String name) {
		this.color = color;
		try {
			this.music = new Music("assets/sounds/" + music, true);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.biomeName = name;
	}

	public Color getColor() {
		return color;
	}
	
	public Music getMusic() {
		return music;
	}

	public String getName() {
		return biomeName;
	}
}
