package name.tomedds.dupfilefinder;

import name.tomedds.dupfilefinder.path.PathDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Get a list of duplicate files based on their MD5.
 * Check only the files that match criteria specified in a configuration file.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    CsvGenerator csvGenerator;

    @Autowired
    FilterConfig filterConfig;

    private static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {

        if (args.length < 1) {
            LOGGER.error("Usage: much specify at least one path");
        }

        // define Predicate to be used in filtering search
        MatchUsingConfig matchUsingConfig = new MatchUsingConfig(filterConfig);

        // collect a list of files that match the filter
        ArrayList<Path> fullPathList = new ArrayList<>();

        // TODO: try to convert this loop to a stream later
        for (String root : args) {

            try {
                fullPathList.addAll(Files.find(Paths.get(root), Integer.MAX_VALUE, (p, b) ->
                        matchUsingConfig.test(p, b))
                        .collect(Collectors.toList()));

            } catch (IOException ex) {
                LOGGER.error("IOExcepton reading file inside " + root);
            }

        }

        LOGGER.debug("found " + fullPathList.size() + " files.");

        // Construct a list of PathDetail instances using the filtered list
        List<PathDetail> selectedFiles = fullPathList.stream().map(p -> new PathDetail(p)).collect(Collectors.toList());

        // Create a Map in which the key is an MD5 and the value is a list of files with that MD5
        Map<String, List<PathDetail>> md5Map = MapBuilder.constructMd5Map(selectedFiles);

        // filter this map to get entries where the list size > 1
        Map<String, List<PathDetail>> dupMap = md5Map.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .collect(Collectors.toMap(md5 -> md5.getKey(), pdList -> pdList.getValue()));

        // Generate a CSV listing the duplicates

        csvGenerator.generateCsv(dupMap);

    }
}