package tests;

import static org.junit.Assert.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import plugin.GUIPlugin;
import plugin.Plugin;
import plugin.TextPlugin;

public class TestPlugin extends Plugin implements TextPlugin {

	public static final String PLUGIN_ID = "This is a test";

	public TestPlugin() {
		super(PLUGIN_ID);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setText(JLabel label) {
		// TODO Auto-generated method stub

	}
}

public class testing {

	@Before
	public void setUp() throws Exception {
		TestPlugin testPlugin = new TestPlugin(id)
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertFalse(false);
		// fail("Not yet implemented");
	}

}
