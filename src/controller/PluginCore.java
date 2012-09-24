package controller;

import java.awt.EventQueue;
import java.util.HashMap;

import plugin.Plugin;

import view.HostView;

public class PluginCore {

	// For holding registered plugin
	public static HashMap<String, Plugin> idToPlugin;

	// Plugin manager
	PluginManager pluginManager;
	HostView GUI = new HostView("Brahma host View");

	public PluginCore() {
		idToPlugin = new HashMap<String, Plugin>();

		// Start the plugin manager now that the core is ready
		try {
			this.pluginManager = new PluginManager(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread thread = new Thread(this.pluginManager);
		thread.start();
	}

	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUI.pack();
				GUI.setVisible(true);
			}
		});
	}

	public void stop() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUI.setVisible(false);
			}
		});
	}

	public void addPlugin(Plugin plugin) {
		this.idToPlugin.put(plugin.getId(), plugin);
		GUI.add(plugin);
	}

	public void removePlugin(String id) {
		Plugin plugin = this.idToPlugin.remove(id);
		// Stop the plugin if it is still running

		GUI.removed(plugin);
	}
}
