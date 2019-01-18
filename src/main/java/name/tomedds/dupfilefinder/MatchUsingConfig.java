package name.tomedds.dupfilefinder;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * See if the current file matches the criteria from the configuration.
 *
 * TODO: add additional criteria for file suffix, last modified date
 */

public class MatchUsingConfig implements MatchPredicate {

    static FilterConfig filterConfig;

    public MatchUsingConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public Boolean test(Path path, BasicFileAttributes bfa) {
        // for now, just test file size
        return (bfa.isRegularFile() && bfa.size()  > filterConfig.getMinFileSize());
    }


}
