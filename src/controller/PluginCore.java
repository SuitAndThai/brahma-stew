package controller;

import java.awt.EventQueue;
import java.util.HashMap;

import plugin.Plugin;

import view.GUIWhyNot;

public class PluginCore {

	// For holding registered plugin
	public static HashMap<String, Plugin> idToPlugin;

	// Plugin manager
	PluginManager pluginManager;
	GUIWhyNot GUI = new GUIWhyNot();

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
				GUI.frame.pack();
				GUI.frame.setVisible(true);
			}
		});
	}

	public void stop() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUI.frame.setVisible(false);
			}
		});
	}

	public void addPlugin(Plugin plugin) {
		this.idToPlugin.put(plugin.getId(), plugin);
		GUI.listModel.addElement(plugin.getId());
		GUI.add(plugin);
	}

	public void removePlugin(String id) {
		Plugin plugin = this.idToPlugin.remove(id);
		GUI.listModel.removeElement(id);

		// Stop the plugin if it is still running
		plugin.stop();

		GUI.removed(plugin);
	}
}
