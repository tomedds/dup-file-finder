package name.tomedds.dupfilefinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;


/**
 * Overview for application:
 * <p>
 * - Get starting point in filesystem.
 * <p>
 * - Do a recursive search of files (not directories)
 * - for each file:
 * -- get the size and the MD5 checksum
 * -- store in the db table the directory, filename, checksum, size?
 * - determine where there are duplicates
 * - create a CSV with the results
 */

@Component
public class CsvGenerator {

    private static String CSV_FILE = "./duplicateFiles.csv";

    final Logger LOGGER = LoggerFactory.getLogger(CsvGenerator.class);

    public CsvGenerator() {
    }


    /**
     * @param duplicateFiles
     */
    public void generateCsv(Map<String, List<PathDetail>> duplicateFiles) {

        // find the item with the maximum number of duplicates

        OptionalInt maxFiles = duplicateFiles.values().stream().mapToInt(pd -> pd.size()).max();

        if (!maxFiles.isPresent()) {
            LOGGER.info("No duplicate files found.");
            return;
        }

        // build the header. We won't print more than 255 duplicates for a given MD5
        //  File1, File2, File3, File4" + System.lineSeparator();

        StringBuffer csvHeaderBuff = new StringBuffer("NumDups, Size, ");
        for (int i = 0; i < maxFiles.getAsInt() && i < 255; i++) {
            csvHeaderBuff.append("File" + i);
            if (i < -1 + maxFiles.getAsInt()) {
                csvHeaderBuff.append(", ");
            }
        }

        csvHeaderBuff.append(System.lineSeparator());

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE));
            writer.write(csvHeaderBuff.toString());

        /*
            Iterate over the values. For each group of duplicates, print the size from the first entry
            (they are all the same) and then the path names
        */

            for (List<PathDetail> dupsList : duplicateFiles.values()) {

                StringBuffer outBuf = new StringBuffer(String.valueOf(dupsList.size()))
                        .append(",")
                        .append(String.valueOf(dupsList.get(0).getFileSize()))
                        .append(",");

                outBuf.append(dupsList.stream()
                        .sorted((detail1, detail2) -> detail1.getPath().toString().compareToIgnoreCase(detail2.getPath().toString()))
                        .map(pathDetails -> pathDetails.getPath().toString()).limit(255)
                        .collect(joining(",")));

                //  String filesString = "\"" + String.join("\",\"", filePaths) + "\"";
                outBuf.append(System.lineSeparator());
                writer.write(outBuf.toString());
            }
            writer.close();

        } catch (IOException ex) {
            LOGGER.error("Unable to create output file ", ex);
        }

    }


}
