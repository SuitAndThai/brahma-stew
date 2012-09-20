package host;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class PluginLoader implements Runnable {
	private HashMap<String, File> pluginList;
	private static final String pluginsPath = "plugins/";

	public PluginLoader() {
		this.pluginList = new HashMap<String, File>();
	}

	@Override
	public void run() {
		// Load existing plugins
		File pluginDirectory = new File(pluginsPath);
		for (File f : pluginDirectory.listFiles()) {
			this.pluginList.put(f.getName(), f);
		}

		// TODO: constantly listen for new plugins on a new thread
		// similar to watchDir.processEvents()
	}

	public IPlugin loadPlugin(String fileName) throws IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		// get jar file
		File jarBundle = pluginList.get(fileName);
		JarFile jarFile = new JarFile(jarBundle);

		// get manifest in the jar file
		Manifest mf = jarFile.getManifest();
		Attributes mainAttribs = mf.getMainAttributes();

		// get Plugin-Class attribute and load the class
		URL[] urls = new URL[] { jarBundle.toPath().toUri().toURL() };
		ClassLoader classLoader = new URLClassLoader(urls);
		String className = mainAttribs.getValue("Plugin-Class");
		Class<?> pluginClass = classLoader.loadClass(className);

		// release jar resources
		jarFile.close();

		return (IPlugin) pluginClass.newInstance();
	}
}
