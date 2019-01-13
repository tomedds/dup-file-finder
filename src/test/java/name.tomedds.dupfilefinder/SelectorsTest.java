package name.tomedds.dupfilefinder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class SelectorsTest {

	long minFileSize = 5120;

	final Logger LOGGER = LoggerFactory.getLogger(SelectorsTest.class);

	@Before
	public void beforeTest() {
	}

	@Test
	public void selectorFalseForSmallFile() {
		long fileSize = 1024;
		LOGGER.debug("verifying that file IS NOT selected for size=" + fileSize);
		Assert.assertFalse(Selectors.matchesCriteria("dummy", fileSize, null, minFileSize));

	}

	@Test
	public void selectorTrueForLargeFile() {
		long fileSize = 1048576;
		LOGGER.debug("verifying that file IS selected for size=" + fileSize);
		Assert.assertTrue(Selectors.matchesCriteria("dummy", fileSize, null, minFileSize));
	}

}