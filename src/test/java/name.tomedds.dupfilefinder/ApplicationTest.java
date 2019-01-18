package name.tomedds.dupfilefinder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	@Autowired
	private FilterConfig filterConfig;

	Logger LOGGER = LoggerFactory.getLogger(ApplicationTest.class);

	@Test
	public void contextLoads() {
		LOGGER.info("testing ability to load Spring context");
		assertThat(filterConfig).isNotNull();

	}


}