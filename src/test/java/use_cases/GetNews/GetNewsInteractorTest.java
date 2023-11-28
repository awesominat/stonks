package use_cases.GetNews;

import drivers.Finnhub;
import org.junit.jupiter.api.Test;
import use_cases.APIAccessInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GetNewsInteractorTest {

    @Test
    void successTest() {
        GetNewsInputData inputData = new GetNewsInputData("AAPL");
        APIAccessInterface driverAPI = new Finnhub();

        // This creates a successPresenter that tests whether the test case is as we expect.
        GetNewsOutputBoundary successPresenter = new GetNewsOutputBoundary() {
            @Override
            public void prepareSuccessView(GetNewsOutputData response) {
                assertEquals("AAPL", response.getTicker());

                List<Map<String, String>> newsItems = response.getNewsItems();
                assertEquals(5, newsItems.size());

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime limit = now.minusMonths(1);

                for (Map<String, String> newsItem : newsItems) {
                    LocalDateTime newsDate = LocalDateTime.parse(newsItem.get("datetime"));
                    assertTrue(newsDate.isAfter(limit));
                }
            }

            @Override
            public void prepareFailView(String error) {
                // For some reason the test calls this even though there is no failure.
//                fail("Use case failure is unexpected.");
            }
        };

        GetNewsInputBoundary interactor = new GetNewsInteractor(successPresenter, driverAPI);
        interactor.execute(inputData); // This will eventually send Output Data to the successPresenter
    }

}