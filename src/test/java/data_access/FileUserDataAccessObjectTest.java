package data_access;

import entity.CommonUser;
import entity.CommonUserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class FileUserDataAccessObjectTest {

    private String testJsonPath;
    private FileUserDataAccessObject fileUserDataAccessObject;

    @BeforeEach
    void setUp() {
        try {
            testJsonPath = "testUserData.json";
            fileUserDataAccessObject = new FileUserDataAccessObject(testJsonPath, new CommonUserFactory());
        } catch (IOException e) {
            fail();
        }
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(testJsonPath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void saveAndGet() {
        fileUserDataAccessObject.get().addBalance(500.0);

        fileUserDataAccessObject.save();

        FileUserDataAccessObject newFileUserDataAccessObject;
        try {
            newFileUserDataAccessObject = new FileUserDataAccessObject(testJsonPath, new CommonUserFactory());
            CommonUser newUser = (CommonUser) newFileUserDataAccessObject.get();

            assertEquals(newUser.getBalance(), 10500.0);
        } catch (IOException e) {
            fail("Exception occurred while setting up the test");
        }
    }
}
