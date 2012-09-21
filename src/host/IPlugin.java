package host;

import javax.swing.JPanel;

public abstract class IPlugin {
	private String id;

	public IPlugin(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public abstract void layoutPanel(JPanel panel);

	public abstract void start();

	public abstract void stop();
}
