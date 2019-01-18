package name.tomedds.dupfilefinder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Define the configuration used in the file search
 */

@Component
public class FilterConfig {

    private int minFileSize;

    public FilterConfig(@Value("${dupfilefinder.minFileSize}") final int minFileSize) {
        // convert the user-specified value to K, e.g., 5 -> 5K
        this.minFileSize = 1024*minFileSize;
    }

    public int getMinFileSize() {
        return minFileSize;
    }
}
