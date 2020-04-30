package util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVReader {
    public static void readSegmentCSV(String csvPath) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(csvPath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());

        for (CSVRecord cr: csvParser) {
           System.out.println(cr.get("speaker_label"));
           System.out.println(cr.get("start_timestamp"));
           System.out.println(cr.get("end_timestamp"));
           System.out.println(cr.get("color"));
        }
    }
}
