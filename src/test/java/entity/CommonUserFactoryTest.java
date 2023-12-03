package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonUserFactoryTest {

    @Test
    void create() {
        // Create an instance of the factory
        CommonUserFactory userFactory = new CommonUserFactory();

        // Call the create method to create a CommonUser
        User commonUser = userFactory.create();

        // Verify that the CommonUser object is not null
        assertNotNull(commonUser);

        // Verify the initial balance
        assertEquals(10000.0, commonUser.getBalance());

        // Verify the initial portfolio (assuming it's empty in the CommonUser constructor)
        assertTrue(commonUser.getPortfolio().isEmpty());

        // Verify the initial transaction history (assuming it's empty in the CommonUser constructor)
        assertTrue(commonUser.getHistory().isEmpty());
    }

}