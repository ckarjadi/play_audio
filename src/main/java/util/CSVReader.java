package util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVReader {
    public static ArrayList<HashMap<String, String>> readSegmentCSV(String csvPath) throws IOException {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        Reader reader = Files.newBufferedReader(Paths.get(csvPath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());

        for (CSVRecord cr: csvParser) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("speaker", cr.get("speaker"));
            hm.put("start", cr.get("start"));
            hm.put("end", cr.get("end"));
            hm.put("color", cr.get("color"));
            arrayList.add(hm);
        }
        return arrayList;
    }
}
