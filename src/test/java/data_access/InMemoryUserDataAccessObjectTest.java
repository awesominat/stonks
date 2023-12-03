package data_access;

import entity.CommonUserFactory;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryUserDataAccessObjectTest {

    private String testJsonPath;
    private InMemoryUserDataAccessObject inMemoryUserDataAccessObject;

    @BeforeEach
    void setUp() {
        inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject(new CommonUserFactory());
    }

    @Test
    void saveAndGet() {
        assertNotNull(inMemoryUserDataAccessObject.get());
        User user = new CommonUserFactory().create();
        assertEquals(inMemoryUserDataAccessObject.get().getBalance(), user.getBalance());
        assertEquals(inMemoryUserDataAccessObject.get().getPortfolio(), user.getPortfolio());
        assertEquals(inMemoryUserDataAccessObject.get().getHistory(), user.getHistory());
    }
}
