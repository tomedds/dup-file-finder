package name.tomedds.dupfilefinder;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

/**
 * Implementation of FileVisitor used in walking the file tree.
 * Exclude files that are smaller than the specified size,
 * and collect attributes (size, MD5) for each selected file.
 *
 */
@Component
public class PathDetailsVisitor extends SimpleFileVisitor<Path> {

    @Value("${dupfilefinder.minFileSize}")
    private Integer minFileSize;

    private List<PathDetail> pathDetails;

    final Logger LOGGER = LoggerFactory.getLogger(PathDetailsVisitor.class);

    @Autowired
    public PathDetailsVisitor() {
    }

    // Populate the file details for file

    @Override
    public FileVisitResult visitFile(Path path,  BasicFileAttributes attr) {

        if (attr.isRegularFile()) {

            if (attr.size() > 1024 * minFileSize) {

                try {
                    pathDetails.add(new PathDetail(path, attr.size(),
                            new DigestUtils(MD5).digestAsHex(path.toFile())));
                } catch (IOException ex) {
                    LOGGER.error("Error trying to get MD5 for file " + path.getFileName(), ex);
                }
            } else {
               // LOGGER.info("Skipping small file " + path.toString());
            }

        }

        return CONTINUE;
    }

    // start a new list for each root

    public void resetPathDetails() {
        this.pathDetails = new ArrayList<>();
    }

    public List<PathDetail> getPathDetails() {
        return pathDetails;
    }


}
