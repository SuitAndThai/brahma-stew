package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.PluginCore;

import plugin.Plugin;

public class HostView extends JFrame {
	private JPanel contentPane;
	public JLabel bottomLabel;
	private JList sideList;
	public DefaultListModel<String> listModel;
	private JPanel centerEnvelope;
	private Plugin currentPlugin;

	public HostView(String title) {
		// Lets create the elements that we will need
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = (JPanel) this.getContentPane();
		contentPane.setPreferredSize(new Dimension(700, 500));
		bottomLabel = new JLabel("No plugins registered yet!");

		listModel = new DefaultListModel<String>();
		JList jList = new JList(this.listModel);
		sideList = jList;
		sideList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sideList.setLayoutOrientation(JList.VERTICAL);
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
						int index = sideList.getSelectedIndex();
						String id = listModel.elementAt(index);

						if (PluginCore.idToPlugin.get(id) == null
								|| PluginCore.idToPlugin.get(id).equals(
										currentPlugin))
							return;

						// Stop previously running plugin
						if (currentPlugin != null)
							currentPlugin.stop();

						// The newly selected plugin is our current plugin
						currentPlugin = PluginCore.idToPlugin.get(id);

						// Clear previous working area
						centerEnvelope.removeAll();

						// Create new working area
						JPanel centerPanel = new JPanel();
						centerEnvelope.add(centerPanel, BorderLayout.CENTER);

						// Ask plugin to layout the working area
						currentPlugin.layout(centerPanel);
						contentPane.revalidate();
						contentPane.repaint();

						// Start the plugin
						currentPlugin.start();

						bottomLabel.setText("The " + currentPlugin.getId()
								+ " is running!");
					}
				});
	}

	public void removed(Plugin plugin) {
		this.listModel.addElement(plugin.getId());
		plugin.stop();
		bottomLabel.setText("The " + plugin.getId()
				+ " plugin has been recently removed!");

	}

	public void add(Plugin plugin) {
		this.listModel.removeElement(plugin.getId());
		
		bottomLabel.setText("The " + plugin.getId()
				+ " plugin has been recently added!");

	}
}
