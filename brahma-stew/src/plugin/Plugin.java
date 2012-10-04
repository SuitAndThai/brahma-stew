package plugin;

import javax.swing.JPanel;

public abstract class Plugin {
	private String id;

	public Plugin(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// Callback method

	public abstract void start();

	public abstract void stop();

}

// README: If your plugin wishes to use a GUI please write code for implementing
// "GUIPlugin". If you wish to use the Text label at the bottom of the screen,
// please use the "TextPlugin", you may use both, or neither one.