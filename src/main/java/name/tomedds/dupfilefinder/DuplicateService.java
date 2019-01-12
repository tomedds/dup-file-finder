package name.tomedds.dupfilefinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Overview for application:
 * <p>
 * - Get starting point in filesystem.
 * <p>
 * - Do a recursive search of files (not directories)
 * - for each file:
 * -- get the size and the MD5 checksum
 * -- store in the db table the directory, filename, checksum, size?
 * - determine where there are duplicates
 * - create a CSV with the results
 */

@Component
public class DuplicateService {

    final Logger LOGGER = LoggerFactory.getLogger(DuplicateService.class);

    public DuplicateService() {
    }

    /**
     * Obtain a list of the files found in the given paths and determine which are duplicates
     *
     * @param roots
     */
    public List<List<PathDetail>> identifyDuplicates(String[] roots) {

        List<PathDetail> fullList = Arrays.stream(roots)
                .map(root -> this.listFileTree(root))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        LOGGER.debug("found details for " + fullList.size() + " files.");

        LOGGER.error("using dummy code in method");

        // for now, we have no duplicates

        return new ArrayList(new ArrayList<PathDetail>());
    }

    /**
     *
     * @param duplicateFiles
     */
    public void generateCsv(List<List<PathDetail>> duplicateFiles) {
        LOGGER.debug("generating CSV for " + duplicateFiles.size() + " files.");
    }

    /**
     * Get a list of Paths for the files in the specified directory.
     *
     * @param root
     * @return
     */
    private final List<PathDetail> listFileTree(String root) {

        List<PathDetail> pathDetails = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(root), new PathDetailsVisitor(pathDetails));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pathDetails;

    }

}
