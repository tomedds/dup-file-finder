package name.tomedds.dupfilefinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


/**
    Process a list of files checking for duplicates
  */

@Service
public class DuplicateService {

    @Autowired
    PathDetailsVisitor pathDetailsVisitor;

    final Logger LOGGER = LoggerFactory.getLogger(DuplicateService.class);

    public DuplicateService() {
    }


    /**
     * Obtain a list of the files found in the given paths and determine which are duplicates
     *
     * @param roots
     */
    public Map<String, List<PathDetail>> identifyDuplicates(String[] roots) {

        /*
           Collect the list of files for review.
         */

        List<PathDetail> fullList = Arrays.stream(roots)
                .map(root -> this.listFileTree(root))
                .flatMap(List::stream)
                .collect(Collectors.toList());


        for (PathDetail pd1: fullList) {
            System.out.println(pd1.getPath().toString());
        }

        LOGGER.debug("found details for " + fullList.size() + " files.");

        Map<String, List<PathDetail>> detailMap = new HashMap<>();

        for (PathDetail pd : fullList) {

            // If the Map already has this key, then add the new item to the existing (List) value
            // Otherwise add the new list

            if (detailMap.containsKey(pd.getMd5())) {
                List currList = detailMap.get(pd.getMd5());
                currList.add(pd);
                detailMap.put(pd.getMd5(), currList);
            } else {
                detailMap.put(pd.getMd5(), new ArrayList<>(Arrays.asList(pd)));
            }

        }

        // now we pull each entry with a list size > 1
        Map<String, List<PathDetail>> dupMap = detailMap.entrySet().stream().filter(pd -> pd.getValue().size() > 1)
                .collect(Collectors.toMap(pd -> pd.getKey(), pd -> pd.getValue()));

        return dupMap;
    }


    /**
     * Get a list of Paths for the files in the specified directory.
     *
     * @param root
     * @return
     */

   private final List<PathDetail> listFileTree(String root) {

        LOGGER.debug("searching " + root);
        
        pathDetailsVisitor.resetPathDetails();

        try {
            Files.walkFileTree(Paths.get(root), pathDetailsVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.debug("found " + pathDetailsVisitor.getPathDetails().size() + " files in " + root);
        return pathDetailsVisitor.getPathDetails();

    }

}
