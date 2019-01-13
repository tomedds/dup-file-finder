package name.tomedds.dupfilefinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    DuplicateService duplicateService;

    @Autowired
    CsvGenerator csvGenerator;

    @Override
    public void run(String... args) {

        if (args.length < 1) {
            LOGGER.error("Usage: much specify at least one path");
        }

        // Determine the list of duplicates and then generate a CSV with the results
        csvGenerator.generateCsv( duplicateService.identifyDuplicates(args));

    }
}