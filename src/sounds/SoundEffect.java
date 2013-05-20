package sounds;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import config.Config;

public class SoundEffect {

	private Sound sound;
	
	public SoundEffect(String directory) { //example: "assets/sounds/Jump_Sound.wav"
		try {
			sound = new Sound(directory);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void play() {
		if (Config.soundOn) sound.play(1, Config.gameVolume);
	}

}
