package name.tomedds.dupfilefinder.path;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

public final class PathDetail {

    private Path path;
    private long fileSize;
    private String md5;

    final Logger LOGGER = LoggerFactory.getLogger(PathDetail.class);

    public PathDetail(Path path) {
        this.path = path;
        this.fileSize = fileSize;
        this.md5 = md5;

        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            this.fileSize = attrs.size();
            this.md5 = new DigestUtils(MD5).digestAsHex(path.toFile());

        } catch (IOException ex) {
            LOGGER.error("IOException trying to get attributes for " + path.toString());
            throw new RuntimeException(ex);
        }

    }

  /*  public PathDetail(Path path, long fileSize, String md5) {
        this.path = path;
        this.fileSize = fileSize;
        this.md5 = md5;
    }

    public PathDetail(Path path, BasicFileAttributes attrs, String md5) {
        this.path = path;
        this.fileSize = attrs.size();
        this.md5 = md5;
    }
*/
    public Path getPath() {
        return path;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getMd5() {
        return md5;
    }


}
