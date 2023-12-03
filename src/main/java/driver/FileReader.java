package driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReader {
    /**
     * Reads the contents of a file and returns them as a single string.
     * Used to read the API key from file.txt
     *
     * @param inputStream   an input stream object constructed from the file
     * @return              a single string containing the contents of the file
     */
    public String readFromInputStream(InputStream inputStream) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = br.readLine();
            resultStringBuilder.append(line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultStringBuilder.toString();
    }
}
