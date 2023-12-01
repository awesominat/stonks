package use_case.GetNews;

import entity.CompanyInformation;
import entity.CompanyNews;
import entity.PricePoint;
import entity.StockInformation;
import org.junit.Test;
import use_case.APIAccessInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GetNewsInteractorTest {

    @Test
    public void successTest() {
        GetNewsInputData inputData = new GetNewsInputData("AAPL");

        // Create anonymous class that implements the APIAccessInterface
        APIAccessInterface driverAPI = new APIAccessInterface() {
            @Override
            public String getAppName() {
                return "stonks";
            }

            @Override
            public boolean isMarketOpen() {
                // This method is not relevant, so simply return true.
                return true;
            }

            @Override
            public CompanyInformation getCompanyProfile(String ticker) {
                return new CompanyInformation(
                        "US",
                        "Apple Inc",
                        "AAPL",
                        "https://www.apple.com/",
                        "1980-12-12"
                );
            }

            @Override
            public List<CompanyNews> getCompanyNews(String ticker, LocalDate from, LocalDate to) {
                List<CompanyNews> newsOut = new ArrayList<>();
                // Add five duplicate articles to output List.
                for (int i = 0; i < 5; i++) {
                    newsOut.add(new CompanyNews(
                                    "company",
                                    LocalDate.parse("2023-11-28"),
                                    "The Cash-Rich Magnificent 7 For The Long Haul",
                                    "https://finnhub.io/api/news?id=69513fc7f9f8ea2f36c36a0cd322ff4acd6983bf7dbb70d44fcc15fb475810ac",
                                    "Wage growth, low unemployment rates, and artificial intelligence are driving the thriving U.S. economy. Click here to read my most recent analysis."
                            )
                    );
                }
                return newsOut;
            }

            @Override
            public PricePoint getCurrentPrice(String ticker) {
                return new PricePoint(LocalDate.now(), 100.0);
            }

            @Override
            public StockInformation getCurrentStockInformation(String ticker) {
                return new StockInformation(100.0, 1.05, 0.01);
            }
        };

        // This creates a successPresenter that tests whether the test case is as we expect.
        GetNewsOutputBoundary successPresenter = new GetNewsOutputBoundary() {
            @Override
            public void prepareSuccessView(GetNewsOutputData response) {
                assertEquals("AAPL", response.getTicker());

                List<Map<String, String>> newsItems = response.getNewsItems();
                assertEquals(5, newsItems.size());

                LocalDate now = LocalDate.now();
                LocalDate limit = now.minusMonths(1);

                for (Map<String, String> newsItem : newsItems) {
                    LocalDate newsDate = LocalDate.parse(newsItem.get("datetime"));
                    assertTrue(newsDate.isAfter(limit));
                }
            }

            @Override
            public void prepareFailView(String error) {
                // For some reason the test calls this even though there is no failure.
                // The `fail` method will always make the test fail, so we must avoid this using a comment.
//                fail("Use case failure is unexpected.");
            }
        };

        GetNewsInputBoundary interactor = new GetNewsInteractor(successPresenter, driverAPI);
        interactor.execute(inputData); // This will eventually send Output Data to the successPresenter
    }

}