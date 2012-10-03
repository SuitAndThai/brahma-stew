package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import plugin.GUIPlugin;
import plugin.Plugin;
import plugin.TextPlugin;
import controller.PluginCore;

public class HostView extends JFrame implements KeyListener {
	private TextPlugin TextPlugin;
	private JPanel contentPane;
	public JLabel bottomLabel;
	private JList sideList;
	public DefaultListModel<String> listModel;
	private JPanel centerEnvelope;
	private Plugin currentPlugin;
	private ArrayList<Plugin> currentPlugins;
	protected int index;

	public HostView(String title) {
		// Lets create the elements that we will need
		this.setTitle("Welcome to the Plugin Manager!");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = (JPanel) this.getContentPane();
		contentPane.setPreferredSize(new Dimension(700, 500));
		bottomLabel = new JLabel();
		currentPlugins = new ArrayList<Plugin>();
		listModel = new DefaultListModel<String>();
		JList jList = new JList(this.listModel);
		sideList = jList;
		sideList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sideList.setLayoutOrientation(JList.VERTICAL);
		sideList.addKeyListener(this);
		JScrollPane scrollPane = new JScrollPane(sideList);
		scrollPane.setPreferredSize(new Dimension(100, 50));
		// Create center display area
		centerEnvelope = new JPanel(new BorderLayout());
		centerEnvelope
				.setBorder(BorderFactory.createLineBorder(Color.black, 5));

		// Lets lay them out, contentPane by default has BorderLayout as its
		// layout manager
		contentPane.add(centerEnvelope, BorderLayout.CENTER);
		contentPane.add(scrollPane, BorderLayout.EAST);
		contentPane.add(bottomLabel, BorderLayout.SOUTH);
		// Add action listeners
		sideList.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						// If the list is still updating, return
						if (e.getValueIsAdjusting())
							return;

						// List has finalized selection, let's process further
						index = sideList.getSelectedIndex();

					}
				});
	}

	public void removed(Plugin plugin) {
		this.listModel.removeElement(plugin.getId());
		plugin.stop();
		this.setTitle("The " + plugin.getId()
				+ " plugin has been recently removed!");

	}

	public void add(Plugin plugin) {
		this.listModel.addElement(plugin.getId());
		this.setTitle("The " + plugin.getId()
				+ " plugin has been recently added!");

	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		keys(e);
	}

	public void keys(KeyEvent e) {
		switch (e.getKeyChar()) {

		// PRESS ENTER TO START A NEW PLUGIN
		case KeyEvent.VK_ENTER:

			String id = listModel.elementAt(index);
			if (PluginCore.idToPlugin.get(id) == null) {
				return;
			}
			// The newly selected plugin is our current
			currentPlugin = PluginCore.idToPlugin.get(id);
			if (!currentPlugins.contains(currentPlugin)) {
				currentPlugins.add(currentPlugin);
			}
			// Clear previous working area
			centerEnvelope.removeAll();

			// Create new working area

			JPanel centerPanel = new JPanel();
			centerEnvelope.add(centerPanel, BorderLayout.CENTER);

			// Ask plugin to layout the working area
			if (currentPlugin instanceof GUIPlugin) {
				((plugin.GUIPlugin) currentPlugin).layout(centerPanel);
				contentPane.revalidate();
				contentPane.repaint();

			}
			this.setTitle("The " + currentPlugin.getId()
					+ " plugin has been started!");
			// Start the plugin
			currentPlugin.start();
			if (currentPlugin instanceof TextPlugin) {
				((plugin.TextPlugin) currentPlugin).setText(bottomLabel);
			} else {
				bottomLabel.setText("The " + currentPlugin.getId()
						+ " is Running!");
			}

			break;

		// PRESS DELETE TO STOP THE SELECTED PLUGIN
		case KeyEvent.VK_DELETE:
			int index = sideList.getSelectedIndex();
			String id2 = listModel.elementAt(index);
			if (PluginCore.idToPlugin.get(id2) != null) {
				for (Plugin plugin : currentPlugins) {
					if (PluginCore.idToPlugin.get(id2).equals(plugin)) {
						if (plugin instanceof GUIPlugin) {
							centerEnvelope.removeAll();
							contentPane.revalidate();
							contentPane.repaint();
						}

						currentPlugins.remove(plugin);
						plugin.stop();
						this.setTitle("The" + plugin.getId()
								+ " plugin has been removed");
						bottomLabel.setText("");
						return;

					}
				}
			}
			break;
		}
	}
}
