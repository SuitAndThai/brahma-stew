package host;

import java.io.IOException;
import java.util.HashMap;

public class BrahmaStewHost {
	public BrahmaStewHost() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, IOException {
		// load plugins
		PluginLoader loader = new PluginLoader();
		IPlugin plugin = loader.loadPlugin("foo");
		// no idea how i'm starting this

		// host application goes here
	}
}
