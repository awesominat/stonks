package data_access;

import entity.CommonUser;
import entity.CommonUserFactory;
import entity.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileUserDataAccessObjectTest {

    private String testJsonPath;
    private FileUserDataAccessObject fileUserDataAccessObject;

    @BeforeEach
    void setUp() {
        try {
            // Create a temporary directory and file path for testing
            testJsonPath = "testUserData.json";
            fileUserDataAccessObject = new FileUserDataAccessObject(testJsonPath, new CommonUserFactory());
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up: delete the test file and directory
        File testFile = new File(testJsonPath);
        if (testFile.exists()) {
            testFile.delete();
        }
        File testDirectory = new File("./");
        if (testDirectory.exists()) {
            testDirectory.delete();
        }
    }

    @Test
    void saveAndGet() {
        // Given
        CommonUser commonUser = new CommonUser();
        commonUser.addBalance(500.0);
        fileUserDataAccessObject.get().addBalance(500.0); // Simulate changes in the loaded user

        // When
        fileUserDataAccessObject.save();

        // Then
        FileUserDataAccessObject newFileUserDataAccessObject;
        try {
            newFileUserDataAccessObject = new FileUserDataAccessObject(testJsonPath, new CommonUserFactory());
            CommonUser newUser = (CommonUser) newFileUserDataAccessObject.get();

            // Verify that the saved user matches the expected user
//            assertEquals(commonUser.getBalance(), newUser.getBalance() + 500.0);
            assertEquals(commonUser.getBalance(), newUser.getBalance());
            // Add more assertions based on your User class methods and data
        } catch (IOException e) {
//            e.printStackTrace();
            fail("Exception occurred while setting up the test");
        }
    }
}
