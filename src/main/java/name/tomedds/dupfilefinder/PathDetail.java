package name.tomedds.dupfilefinder;

import java.nio.file.Path;

public final class PathDetail {

    private Path path;
    private long fileSize;
    private String md5;


    public PathDetail(Path path, long fileSize, String md5) {
        this.path = path;
        this.fileSize = fileSize;
        this.md5 = md5;
    }

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
