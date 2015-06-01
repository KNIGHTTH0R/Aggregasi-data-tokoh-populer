
package datastore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ahmadluky 
 */
public class Csv {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    
    public void writeCsvFile(String fileName, String id, String name, String tweet, String date, String geo) throws IOException {
        
        File file           = new File("data/result/datatwitter/csv/" + fileName +".csv");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            fileWriter.append(id);
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(name);
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(tweet);
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(date);
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(geo);
            fileWriter.append(NEW_LINE_SEPARATOR);
        } catch (Exception e) {
                System.out.println("Error in CsvFileWriter !!!");
                e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }
}
