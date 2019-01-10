package name.tomedds.dupfilefinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    //  Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private static Logger LOGGER = LoggerFactory
            .getLogger(Application.class);

    public static void main(String[] args) {
        LOGGER.info("STARTING THE APPLICATION");
        SpringApplication.run(Application.class, args);
        LOGGER.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        LOGGER.info("EXECUTING : command line runner");

        for (int i = 0; i < args.length; ++i) {
            LOGGER.info("args[{}]: {}", i, args[i]);
        }
    }
}