package name.tomedds.dupfilefinder;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiPredicate;

/**
 * Methods used to select files for review
 *
 * TODO: add additional criteria for file suffix, last modified date
 */

@FunctionalInterface
public interface MatchPredicate {

    Boolean test(Path path, BasicFileAttributes bf);

}
