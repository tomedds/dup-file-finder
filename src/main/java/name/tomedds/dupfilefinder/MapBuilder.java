package name.tomedds.dupfilefinder;

import name.tomedds.dupfilefinder.path.PathDetail;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

/**
 * Maps a PathSee if the current file matches the criteria from the configuration.
 * <p>
 * TODO: add additional criteria for file suffix, last modified date
 */

public class MapBuilder {

    final static Logger LOGGER = LoggerFactory.getLogger(MapBuilder.class);

    public static Map<String, List<PathDetail>> constructMd5Map(List<PathDetail> pathDetailList) {

        HashMap<String, List<PathDetail>> md5Map = new HashMap<>();

        for (PathDetail pathDetail: pathDetailList) {

            if (md5Map.containsKey(pathDetail.getMd5())) {
                LOGGER.debug("adding " + pathDetail.getPath().toString() + " to existing list");
                List currList = md5Map.get(pathDetail.getMd5());
                currList.add(pathDetail);
                md5Map.put(pathDetail.getMd5(), currList);
            } else {
                LOGGER.debug("adding " + pathDetail.getPath().toString() + " as new list");
                md5Map.put(pathDetail.getMd5(), new ArrayList<>(Arrays.asList(pathDetail)));
            }

        }

        return md5Map;
    }

}
