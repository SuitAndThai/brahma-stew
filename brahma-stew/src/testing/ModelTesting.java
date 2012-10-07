package testing;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ModelTesting {

	private static TestPlugin testPlugin;
	private static String expectedId = "foo";

	@Before
	public void setUp() throws Exception {
		testPlugin = new TestPlugin(expectedId);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Assert.assertEquals(testPlugin.getId(), expectedId);

		testPlugin.setId("diff");
		Assert.assertEquals(testPlugin.getId(), "diff");
	}
}
