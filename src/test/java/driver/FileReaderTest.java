package driver;

import data_access.FileUserDataAccessObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class FileReaderTest {

    private final String testReaderPath = "alpha.txt";
    private FileUserDataAccessObject fileUserDataAccessObject;

    @BeforeEach
    void setUp() {
        try {
            FileWriter writer = new FileWriter(testReaderPath);
            writer.write("FileReader works");
            writer.close();
        } catch (IOException e) {
            fail("unable to create file");
        }
    }

    @AfterEach
    void tearDown() {
        File file = new File(testReaderPath);
        if (!file.delete()) {
            fail("unable to delete");
        }
    }

    @Test
    void testTxtReading() {
        File f = new File(testReaderPath);
        FileReader fileReader = new FileReader();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            fail("can't find the file for FileReaderTest");
        }
        assertEquals(fileReader.readFromInputStream(inputStream), "FileReader works");
    }
}
