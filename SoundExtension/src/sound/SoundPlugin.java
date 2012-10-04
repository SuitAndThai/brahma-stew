package sound;

import plugin.Plugin;

/**
 * An extension plugin.
 * 
 * @author Brandon Knight (knightbk@rose-hulman.edu)
 * 
 */
public class SoundPlugin extends Plugin {
	public static final String PLUGIN_ID = "Sound Plugin";
	private MakeSound sound;
	private String filename;

	public SoundPlugin() {
		super(PLUGIN_ID);
		filename = "BGM_Boss.wav";
		sound = new MakeSound(filename);
	}

	@Override
	public void start() {
		sound.prepareSound(filename);
	}

	@Override
	public void stop() {
		sound.stopSound();
		sound.sourceLine.close();
	}

	// For now we need to declare dummy main method
	// to include in manifest file
	public static void main(String[] args) {

	}

}
