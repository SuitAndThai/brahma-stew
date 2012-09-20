package host;

public interface IPlugin {
	public abstract void start();

	public abstract void stop();

	public abstract void load();

	public abstract void unload();
}
