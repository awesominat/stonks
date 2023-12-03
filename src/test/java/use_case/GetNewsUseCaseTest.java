package use_case;

import entity.*;
import interface_adapter.GetNews.GetNewsController;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.GetNews.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetNewsUseCaseTest {
    private APIAccessInterface mockAPI;
    private GetNewsOutputBoundary presenter;

    @BeforeAll
    public void setUpAPI() throws RuntimeException, APIAccessInterface.TickerNotFoundException {
        mockAPI = Mockito.mock(APIAccessInterface.class);

        // Setup mock output for "AAPL" get news call.
        List<CompanyNews> newsOut = new ArrayList<>();
        // Add five duplicate articles to output List.
        for (int i = 0; i < 5; i++) {
            newsOut.add(new CompanyNews(
                            "company",
                            LocalDate.parse("2023-11-28").atStartOfDay(),
                            "The Cash-Rich Magnificent 7 For The Long Haul",
                            "https://finnhub.io/api/news?id=69513fc7f9f8ea2f36c36a0cd322ff4acd6983bf7dbb70d44fcc15fb475810ac",
                            "Wage growth, low unemployment rates, and artificial intelligence are driving the thriving U.S. economy. Click here to read my most recent analysis."
                    )
            );
        }

        // Setup parameters for mock call.
        LocalDate now = LocalDate.now();
        LocalDate monthAgo = now.minusMonths(1);

        // Simulate successful mock call.
        Mockito.when(mockAPI.getCompanyNews("AAPL", monthAgo, now)).thenReturn(newsOut);

        // Simulate ticker error.
        Mockito.when(mockAPI.getCompanyNews("SSS", monthAgo, now)).thenThrow(
                new APIAccessInterface.TickerNotFoundException("Ticker SSS not found. Please enter a valid ticker.")
        );

        // Simulate API failure error.
        Mockito.when(mockAPI.getCompanyNews("API does not respond", monthAgo, now)).thenThrow(
                new RuntimeException("API did not respond.")
        );
    }

    @BeforeEach
    public void setUpPresenter() {
        presenter = Mockito.mock(GetNewsOutputBoundary.class);
    }

    @AfterEach
    public void tearDown() {
        Mockito.clearInvocations(mockAPI);
    }

    @Test
    public void testInfoFetch() {
//        GetNewsInputData inputData = new GetNewsInputData("AAPL");
//        GetNewsInteractor interactor = new GetNewsInteractor(presenter, mockAPI);
//        interactor.execute(inputData);

        GetNewsInteractor interactor = new GetNewsInteractor(presenter, mockAPI);
        GetNewsController controller = new GetNewsController(interactor);
        controller.execute("AAPL");

        ArgumentCaptor<GetNewsOutputData> captor = ArgumentCaptor.forClass(GetNewsOutputData.class);

        Mockito.verify(presenter).prepareSuccessView(captor.capture());
        Mockito.verify(presenter, Mockito.never()).prepareFailView(any(String.class));

        GetNewsOutputData response = captor.getValue();

        Map<String, String> newsItem = response.getNewsItems().get(0);

        assertEquals(5, response.getNewsItems().size());
        assertEquals("AAPL", response.getTicker());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a yyyy-MM-dd");
        LocalDateTime localDateTime = LocalDateTime.parse(newsItem.get("datetime"), formatter);
        String date = localDateTime.format(formatter);
        assertEquals("2023-11-28", localDateTime.toLocalDate().toString());

        assertEquals("The Cash-Rich Magnificent 7 For The Long Haul", newsItem.get("headline"));
        assertEquals("company", newsItem.get("category"));
    }

    @Test
    public void testInvalidTicker() throws APIAccessInterface.TickerNotFoundException {
        GetNewsInputData inputData = new GetNewsInputData("SSS");
        GetNewsInteractor interactor = new GetNewsInteractor(presenter, mockAPI);
        interactor.execute(inputData);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(presenter).prepareFailView(captor.capture());
        Mockito.verify(presenter, Mockito.never()).prepareSuccessView(any(GetNewsOutputData.class));

        String tickerErrorMessage = captor.getValue();

        assertEquals("Ticker 'SSS' not found. Please enter a valid ticker.", tickerErrorMessage);
    }

    @Test
    public void testNoApiResponse() throws RuntimeException {
        GetNewsInputData inputData = new GetNewsInputData("API does not respond");
        GetNewsInteractor interactor = new GetNewsInteractor(presenter, mockAPI);
        interactor.execute(inputData);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(presenter).prepareFailView(captor.capture());
        Mockito.verify(presenter, Mockito.never()).prepareSuccessView(any(GetNewsOutputData.class));

        String apiErrorMessage = captor.getValue();

        assertEquals("API did not respond.", apiErrorMessage);
    }
}
