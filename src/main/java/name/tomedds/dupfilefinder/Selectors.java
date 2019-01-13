package name.tomedds.dupfilefinder;

import java.nio.file.attribute.FileTime;

/**
 * Methods used to select files for review
 *
 * TODO: add additional criteria for file suffix, last modified date
 */

public class Selectors {

    public static Boolean matchesCriteria(String filename, long fileSize, FileTime lastModified, long minFileSize) {

        boolean isSelected = Boolean.FALSE;

        if (fileSize > minFileSize) {
            isSelected = Boolean.TRUE;
        }

        return isSelected;

    }

}
