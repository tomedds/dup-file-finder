package name.tomedds.dupfilefinder;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

public class PathDetailsVisitor extends SimpleFileVisitor<Path> {

    List<PathDetail> pathDetails;

    final Logger LOGGER = LoggerFactory.getLogger(PathDetailsVisitor.class);

    public PathDetailsVisitor(List<PathDetail> pathDetails) {
        this.pathDetails = pathDetails;
    }

// Populate the file details for file

    @Override
    public FileVisitResult visitFile(Path path,
                                     BasicFileAttributes attr) {

        if (attr.isRegularFile()) {
            try {
                pathDetails.add(new PathDetail(path, attr.size(),
                        new DigestUtils(MD5).digestAsHex(path.toFile())));
            } catch (IOException ex) {
                LOGGER.error("Error trying to get MD5 for file " + path.getFileName(), ex);
            }
        }

        return CONTINUE;
    }

}
