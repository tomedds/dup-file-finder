package name.tomedds.dupfilefinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    public Map<String, List<PathDetail>> identifyDuplicates(String[] roots) {

        List<PathDetail> fullList = Arrays.stream(roots)
                .map(root -> this.listFileTree(root))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        /* We should be able to create a Map directly which contains each MD5
        and a list of the files that match it.
         Continue work on this later:
        Map<String, List<PathDetail>> fullMap = Arrays.stream(roots)
                .map(root -> this.listFileTree(root))
                .flatMap(List::stream)
                .collect(Collectors.toMap(pd -> pd.getMd5(), Function.identity(),
                        (s,a) -> ));
                        */

        LOGGER.debug("found details for " + fullList.size() + " files.");

        Map<String, List<PathDetail>> detailMap = new HashMap<>();
        // detailMap.computeIfPresent(pd.getMd5(), (key, value) -> detailMap.get(key).add(Function.identity());

        for (PathDetail pd : fullList) {
            if (detailMap.containsKey(pd.getMd5())) {
                detailMap.get(pd.getMd5()).add(pd);
            }
            else {
                List<PathDetail> pdList = new ArrayList<>();
                pdList.add(pd);
                detailMap.put(pd.getMd5(),pdList);
            }

        }

        // now we pull each entry with a list size > 1
        Map<String, List<PathDetail>> dupMap = detailMap.entrySet().stream().filter(pd -> pd.getValue().size() > 1)
                .collect(Collectors.toMap(pd -> pd.getKey(), pd-> pd.getValue()));

        LOGGER.error("using dummy code in method");

        // for now, we have no duplicates

        return dupMap;
    }

    /**
     * @param duplicateFiles
     */
    public void generateCsv(Map<String, List<PathDetail>> duplicateFiles) {
        LOGGER.debug("generating CSV for " + duplicateFiles.size() + " matches.");
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
