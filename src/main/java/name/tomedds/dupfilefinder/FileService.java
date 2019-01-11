package name.tomedds.dupfilefinder;

import org.springframework.stereotype.Component;

/**
 * Overview for application:
 *
 * - Get starting point in filesystem.
 *
 * - Do a recursive search of files (not directories)
 *   - for each file:
 *      -- get the checksum (or MD5 or some other unique identifier)
 *      -- get the file type from data in the file
 *      -- do we want the size?
 *      -- store in the db table the directory, filename, checksum, size?
 *    - determine where there are duplicates
 *    - create a CSV with the results
 *
 *
 */

@Component

public class FileService {
}
